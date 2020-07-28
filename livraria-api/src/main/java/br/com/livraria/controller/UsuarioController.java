package br.com.livraria.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@Api("Usuario API")
@Slf4j
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/listar")
	@ApiOperation("Procurar usuários")
	public Page<UsuarioDTO> find(UsuarioDTO dto, Pageable pageRequest) {
		Usuario filter = modelMapper.map(dto, Usuario.class);
		Page<Usuario> result = usuarioService.findAllUsuarios(filter, pageRequest);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<UsuarioDTO>(list, pageRequest, result.getTotalElements());
	}

	@PostMapping(value = "/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Criar um usuário")
	public UsuarioDTO salvar(@Valid @RequestBody UsuarioDTO dto) {
		log.info("Criando um usuário por id: {}", dto.getId());
		Usuario usuario = this.modelMapper.map(dto, Usuario.class);
		usuario = usuarioService.salvar(usuario);

		return this.modelMapper.map(usuario, UsuarioDTO.class);
	}

	@PutMapping("/{id}")
	@ApiOperation("Atualizar um usuário")
	public UsuarioDTO editar(@PathVariable(value = "id") Long id, @Valid @RequestBody UsuarioDTO dto) {
		log.info("Atualizar um usuário por id: {}", id);
		return usuarioService.findById(id).map(usuario -> {
			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setSenha(dto.getSenha());
			usuario = usuarioService.editar(usuario);
			return modelMapper.map(usuario, UsuarioDTO.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("Excluir um usuário por id")
	public void excluir(@PathVariable(value = "id") Long id) {
		log.info("Excluir um usuário por id: {}", id);
		Usuario usuario = usuarioService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		usuarioService.excluir(usuario);
	}

	@GetMapping("{id}")
	@ApiOperation("Obter detalhes de um usuário pelo id")
	public UsuarioDTO get(@PathVariable Long id) {
		log.info("Obter detalhes de um usuário pelo id: {}", id);
		return usuarioService.findById(id).map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/nome/{nome}")
	public Page<UsuarioDTO> findByNome(@PathVariable(value = "nome") String nome, Pageable pageable) {
		Page<Usuario> result = usuarioService.findByNome(nome, pageable);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<UsuarioDTO>(list, pageable, result.getTotalElements());

	}

	@GetMapping("/email/{email}")
	public Page<UsuarioDTO> findByEmail(@PathVariable(value = "email") String email, Pageable pageable) {
		Page<Usuario> result = usuarioService.findByEmail(email, pageable);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<UsuarioDTO>(list, pageable, result.getTotalElements());
	}

	@GetMapping("/roles")
	public List<RoleDTO> roles() {
		List<Role> result = usuarioService.findAllRoles();
		return result.stream().map(entity -> modelMapper.map(entity, RoleDTO.class)).collect(Collectors.toList());
	}

	@GetMapping("/traducao")
	public String getMessage(@RequestParam("msg") String msg) {
		return Translator.toLocale(msg);
	}

}
