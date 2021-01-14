package br.com.livraria.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.livraria.config.Translator;
import br.com.livraria.dto.RoleDTO;
import br.com.livraria.dto.UsuarioDTO;
import br.com.livraria.model.Role;
import br.com.livraria.model.Usuario;
import br.com.livraria.service.UsuarioServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@Api(description = "Endpoint para criar, atualizar, deletar, excluir e buscar o Usuário.", tags = {"Usuário API"})
@Slf4j
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "${api.usuario.listar}")
	@GetMapping("/listar")
	public Page<UsuarioDTO> listarUsuarios(UsuarioDTO dto, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
		Usuario filter = modelMapper.map(dto, Usuario.class);
	    PageRequest pageRequest = PageRequest.of(page, size);
		Page<Usuario> result = usuarioService.listarUsuarios(filter, pageRequest);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<UsuarioDTO>(list, pageRequest, result.getTotalElements());
	}

	@ApiOperation("${api.usuario.salvar}")
	@PostMapping(value = "/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO salvar(@Valid @RequestBody UsuarioDTO dto) {
		log.info("Criando um usuário por id: {}", dto.getId());
		Usuario usuario = this.modelMapper.map(dto, Usuario.class);
		usuario = usuarioService.salvar(usuario);

		return this.modelMapper.map(usuario, UsuarioDTO.class);
	}

	@ApiOperation("${api.usuario.editar}")
	@PutMapping("/{id}")
	public UsuarioDTO editar(@PathVariable(value = "id") Long id, @Valid @RequestBody UsuarioDTO dto) {
		log.info("Editar um usuário por id: {}", id);
		return usuarioService.buscarPorId(id).map(usuario -> {
			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setSenha(dto.getSenha());
			usuario = usuarioService.editar(usuario);
			return modelMapper.map(usuario, UsuarioDTO.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@ApiOperation("${api.usuario.excluir}")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable(value = "id") Long id) {
		log.info("Excluir um usuário por id: {}", id);
		Usuario usuario = usuarioService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		usuarioService.excluir(usuario);
	}

	@ApiOperation("${api.usuario.obterId}")
	@GetMapping("{id}")
	public UsuarioDTO buscarPorId(@PathVariable(value = "id") Long id) {
		log.info("Obter detalhes de um usuário pelo id: {}", id);
		return usuarioService.buscarPorId(id).map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@ApiOperation("${api.usuario.buscarNome}")
	@GetMapping("/nome/{nome}")
	public Page<UsuarioDTO> buscarPorNome(@PathVariable(value = "nome") String nome, Pageable pageable) {
		Page<Usuario> result = usuarioService.buscarPorNome(nome, pageable);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<UsuarioDTO>(list, pageable, result.getTotalElements());

	}

	@ApiOperation("${api.usuario.buscarEmail}")
	@GetMapping("/email/{email}")
	public Page<UsuarioDTO> buscarPorEmail(@PathVariable(value = "email") String email, Pageable pageable) {
		Page<Usuario> result = usuarioService.buscarPorEmail(email, pageable);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<UsuarioDTO>(list, pageable, result.getTotalElements());
	}

	@ApiOperation("${api.usuario.listarRoles}")
	@GetMapping("/roles")
	public List<RoleDTO> listarRoles() {
		List<Role> result = usuarioService.listarRoles();
		return result.stream().map(entity -> modelMapper.map(entity, RoleDTO.class)).collect(Collectors.toList());
	}

	@ApiOperation("${api.usuario.fazerTraducao}")
	@GetMapping("/traducao")
	public String fazerTraducao(@RequestParam("msg") String msg) {
		return Translator.toLocale(msg);
	}
	
	
	@ApiOperation("${api.usuario.gerarPDF}")
	 @GetMapping(path = "/pdf")
	  public void gerarPdfUsuario(HttpServletResponse response) throws JRException, IOException {
        try {
        	usuarioService.gerarPdfUsuario(response.getOutputStream());
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=usuarioReport.pdf;");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
	}

}
