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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.livraria.dto.LivroDTO;
import br.com.livraria.model.Livro;
import br.com.livraria.service.impl.LivroServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Api(description = "Endpoint para criar, atualizar, deletar, excluir e buscar o Livro.", tags = {"Livro API"})
@Slf4j
public class LivroController {

	@Autowired
	private LivroServiceImpl livrariaService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "${api.livro.listar}")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna a lista de livro"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping("/listar")
	public Page<LivroDTO> listarLivros(LivroDTO dto, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
		Livro filter = modelMapper.map(dto, Livro.class);
	    PageRequest pageRequest = PageRequest.of(page, size);
		Page<Livro> result = livrariaService.listarLivros(filter, pageRequest);
		List<LivroDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, LivroDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<LivroDTO>(list, pageRequest, result.getTotalElements());
	}

	@ApiOperation(value = "${api.livro.salvar}")
	@PostMapping(value = "/salvar")
	public LivroDTO salvar(@Valid @RequestBody LivroDTO dto) {
		log.info("Criando um livro por isbn: {}", dto.getIsbn());
		Livro entity = this.modelMapper.map(dto, Livro.class);
		entity = livrariaService.salvar(entity);
		return this.modelMapper.map(entity, LivroDTO.class);
	}

	@ApiOperation(value = "${api.livro.editar}")
	@PutMapping("/{id}")
	public LivroDTO editar(@PathVariable(value = "id") Long id, @Valid @RequestBody LivroDTO dto) {

		log.info("Editar um livro por id: {}", id);
		return livrariaService.buscarPorId(id).map(livro -> {
			livro.setAutor(dto.getAutor());
			livro.setTitulo(dto.getTitulo());
			livro = livrariaService.editar(livro);
			return modelMapper.map(livro, LivroDTO.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@ApiOperation("${api.livro.excluir}")
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id) {
		log.info("Excluir um livro por id: {}", id);
		Livro livro = livrariaService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		livrariaService.excluir(livro);
	}
	
    @ApiOperation("${api.livro.obterId}")
	@GetMapping("{id}")
    public LivroDTO buscarPorId(@PathVariable Long id) {
        log.info("Obter detalhes de um livro pelo id: {}", id);
        return livrariaService
                .buscarPorId(id)
                .map( book -> modelMapper.map(book, LivroDTO.class) )
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

	@ApiOperation("${api.livro.buscarTitulo}")
	@GetMapping("/titulo/{titulo}")
	public Page<LivroDTO> buscarPorTitulo(@PathVariable(value = "titulo") String titulo, Pageable pageable) {
		Page<Livro> result = livrariaService.buscarPorTitulo(titulo, pageable);
		List<LivroDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, LivroDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<LivroDTO>(list, pageable, result.getTotalElements());
	}

	@ApiOperation("${api.livro.buscarAutor}")
	@GetMapping("/autor/{autor}")
	public Page<LivroDTO> buscarPorAutor(@PathVariable(value = "autor") String autor, Pageable pageable) {
		Page<Livro> result = livrariaService.buscarPorAutor(autor, pageable);
		List<LivroDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, LivroDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<LivroDTO>(list, pageable, result.getTotalElements());

	}
	
	@ApiOperation("${api.livro.gerarPDF}")
	 @GetMapping(path = "/pdf")
	  public void gerarPdfLivro(HttpServletResponse response) throws JRException, IOException {
       try {
    	   livrariaService.gerarPdfLivro(response.getOutputStream());
           response.setContentType("application/pdf");
           response.addHeader("Content-Disposition", "inline; filename=livroReport.pdf;");
       } catch (JRException | IOException e) {
           e.printStackTrace();
       }
	}


}
