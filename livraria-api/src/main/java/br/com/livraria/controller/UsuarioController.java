package br.com.livraria.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import br.com.livraria.repository.IUsuarioRepository;
import br.com.livraria.service.UsuarioServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@Api(description = "Endpoint para criar, atualizar, deletar, excluir e buscar os Usuários.", tags = {"Usuário API"})
@Slf4j
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@Autowired
	private ModelMapper modelMapper;
	
    @Autowired
    private ApplicationContext context;
    
	private IUsuarioRepository usuarioRepository;
	

	@ApiOperation("Listar usuários")
	@GetMapping("/listar")
	public Page<UsuarioDTO> listarUsuarios(UsuarioDTO dto, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
		Usuario filter = modelMapper.map(dto, Usuario.class);
	    PageRequest pageRequest = PageRequest.of(page, size);
		Page<Usuario> result = usuarioService.listarUsuarios(filter, pageRequest);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<UsuarioDTO>(list, pageRequest, result.getTotalElements());
	}

	@ApiOperation("Criar um usuário")
	@PostMapping(value = "/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO salvar(@Valid @RequestBody UsuarioDTO dto) {
		log.info("Criando um usuário por id: {}", dto.getId());
		Usuario usuario = this.modelMapper.map(dto, Usuario.class);
		usuario = usuarioService.salvar(usuario);

		return this.modelMapper.map(usuario, UsuarioDTO.class);
	}

	@ApiOperation("Atualizar um usuário")
	@PutMapping("/{id}")
	public UsuarioDTO editar(@PathVariable(value = "id") Long id, @Valid @RequestBody UsuarioDTO dto) {
		log.info("Atualizar um usuário por id: {}", id);
		return usuarioService.buscarPorId(id).map(usuario -> {
			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setSenha(dto.getSenha());
			usuario = usuarioService.editar(usuario);
			return modelMapper.map(usuario, UsuarioDTO.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@ApiOperation("Excluir um usuário por id")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable(value = "id") Long id) {
		log.info("Excluir um usuário por id: {}", id);
		Usuario usuario = usuarioService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		usuarioService.excluir(usuario);
	}

	@ApiOperation("Obter detalhes de um usuário pelo id")
	@GetMapping("{id}")
	public UsuarioDTO buscarPorId(@PathVariable Long id) {
		log.info("Obter detalhes de um usuário pelo id: {}", id);
		return usuarioService.buscarPorId(id).map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@ApiOperation("Buscar usuários por nome")
	@GetMapping("/nome/{nome}")
	public Page<UsuarioDTO> buscarPorNome(@PathVariable(value = "nome") String nome, Pageable pageable) {
		Page<Usuario> result = usuarioService.buscarPorNome(nome, pageable);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<UsuarioDTO>(list, pageable, result.getTotalElements());

	}

	@ApiOperation("Buscar usuários por email")
	@GetMapping("/email/{email}")
	public Page<UsuarioDTO> buscarPorEmail(@PathVariable(value = "email") String email, Pageable pageable) {
		Page<Usuario> result = usuarioService.buscarPorEmail(email, pageable);
		List<UsuarioDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<UsuarioDTO>(list, pageable, result.getTotalElements());
	}

	@ApiOperation("Listar roles")
	@GetMapping("/roles")
	public List<RoleDTO> listarRoles() {
		List<Role> result = usuarioService.listarRoles();
		return result.stream().map(entity -> modelMapper.map(entity, RoleDTO.class)).collect(Collectors.toList());
	}

	@ApiOperation("Fazer tradução")
	@GetMapping("/traducao")
	public String fazerTraducao(@RequestParam("msg") String msg) {
		return Translator.toLocale(msg);
	}
	
	
	@ApiOperation("Gerar pdf")
	//  https://github.com/hendisantika/spring-boot-mysql-report
	 @GetMapping(path = "/pdf")
	  public void gerarPdfUsuario(HttpServletResponse response) throws JRException, IOException {
        String message = null;
        try {
        	usuarioService.gerarPdfUsuario(response.getOutputStream());
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=usuarioReport.pdf;");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
	}
	
	//@ApiOperation("Gerar pdf")
	//  https://github.com/hendisantika/spring-boot-mysql-report
	 //@GetMapping(path = "/pdf")
	 //   @ResponseBody
//	    public void getPdf(@PathVariable String jrxml, HttpServletResponse response) throws Exception {
	    public void gerarPdf2(HttpServletResponse response) throws Exception {
	        //Get JRXML template from resources folder
//	        Resource resource = context.getResource("classpath:reports/" + jrxml + ".jrxml");
	        Resource resource = context.getResource("classpath:reports/usuario_list.jrxml");
	        //Compile to jasperReport
	        InputStream inputStream = resource.getInputStream();
	        JasperReport report = JasperCompileManager.compileReport(inputStream);
	        //Parameters Set
	        Map<String, Object> params = new HashMap<>();

	       // List<Car> cars = (List<Car>) carRepository.findAll();
	       // List<Usuario> cars = (List<Usuario>) usuarioRepository.findAll();
	        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

	        //Data source Set
	        JRDataSource dataSource = new JRBeanCollectionDataSource(usuarios);
	        params.put("datasource", dataSource);

	        //Make jasperPrint
	        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
	        //Media Type
	        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
	        //Export PDF Stream
	        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	    }

}
