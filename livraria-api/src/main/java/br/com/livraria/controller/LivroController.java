package br.com.livraria.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.livraria.model.Livro;
import br.com.livraria.service.LivroServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("livros")
public class LivroController {

	@Autowired
	private LivroServiceImpl livrariaService;

	@GetMapping("/listar")
	public List<Livro> listar() {
		return livrariaService.findAll();
	}

	@PostMapping(value = "/salvar")
	public ResponseEntity<Livro> salvar(@Valid @RequestBody Livro livraria) {
		return new ResponseEntity<Livro>(livrariaService.salvar(livraria), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Livro> atualizar(@PathVariable(value = "id") Long livrariaId,
			@Valid @RequestBody Livro livrariaDetails)  {
		Livro livraria = livrariaService.findById(livrariaId);
		livraria.setIsbn(livrariaDetails.getIsbn());
		livraria.setAutor(livrariaDetails.getAutor());
		livraria.setTitulo(livrariaDetails.getTitulo());
		livraria.setPreco(livrariaDetails.getPreco());
		final Livro updatedLivraria = livrariaService.salvar(livraria);
		return ResponseEntity.ok(updatedLivraria);
	}

	@DeleteMapping("/{id}")
	public Map<String, Boolean> remover(@PathVariable(value = "id") Long livrariaId){
		Livro livraria = livrariaService.findById(livrariaId);
		if(livraria != null){
			livrariaService.excluir(livrariaId);
        }
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
