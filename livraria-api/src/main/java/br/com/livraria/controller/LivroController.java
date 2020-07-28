package br.com.livraria.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.livraria.dto.LivroDTO;
import br.com.livraria.model.Livro;
import br.com.livraria.service.LivroServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Api("Livro API")
@Slf4j
public class LivroController {

	@Autowired
	private LivroServiceImpl livrariaService;

	private final ModelMapper modelMapper;

	@GetMapping("/listar")
	@ApiOperation("Procurar livros")
	public Page<LivroDTO> listar(LivroDTO dto, Pageable pageRequest) {
		Livro filter = modelMapper.map(dto, Livro.class);
		Page<Livro> result = livrariaService.findAll(filter, pageRequest);
		List<LivroDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, LivroDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<LivroDTO>(list, pageRequest, result.getTotalElements());
	}

	@PostMapping(value = "/salvar")
	@ApiOperation("Criar um livro")
	public LivroDTO salvar(@Valid @RequestBody LivroDTO dto) {
		log.info("Criando um livro por isbn: {}", dto.getIsbn());
		Livro entity = this.modelMapper.map(dto, Livro.class);
		entity = livrariaService.salvar(entity);
		return this.modelMapper.map(entity, LivroDTO.class);
	}

	@PutMapping("/{id}")
	@ApiOperation("Atualizar um livro")
	public LivroDTO editar(@PathVariable(value = "id") Long id, @Valid @RequestBody LivroDTO dto) {

		log.info("Atualizar um livro por id: {}", id);
		return livrariaService.findById(id).map(livro -> {
			livro.setAutor(dto.getAutor());
			livro.setTitulo(dto.getTitulo());
			livro = livrariaService.editar(livro);
			return modelMapper.map(livro, LivroDTO.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Excluir um livro por id")
	public void excluir(@PathVariable(value = "id") Long id) {
		log.info("Excluir um livro por id: {}", id);
		Livro livro = livrariaService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		livrariaService.excluir(livro);
	}
	
	@GetMapping("{id}")
    @ApiOperation("Obter detalhes de um livro pelo id")
    public LivroDTO get(@PathVariable Long id) {
        log.info("Obter detalhes de um livro pelo id: {}", id);
        return livrariaService
                .findById(id)
                .map( book -> modelMapper.map(book, LivroDTO.class) )
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

	@GetMapping("/titulo/{titulo}")
	public Page<LivroDTO> findByTitulo(@RequestParam(value = "titulo") String titulo, Pageable pageable) {
		Page<Livro> result = livrariaService.findByTitulo(titulo, pageable);
		List<LivroDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, LivroDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<LivroDTO>(list, pageable, result.getTotalElements());
	}

	@GetMapping("/autor/{autor}")
	public Page<LivroDTO> findByAutor(@RequestParam(value = "autor") String autor, Pageable pageable) {
		Page<Livro> result = livrariaService.findByAutor(autor, pageable);
		List<LivroDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, LivroDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<LivroDTO>(list, pageable, result.getTotalElements());

	}

}
