package br.com.livraria.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.livraria.model.Livro;

public interface LivroService {
	Livro salvar(Livro livro);

	Livro editar(Livro livro);

	void excluir(Livro livro);

	Page<Livro> findAll(Livro filter, Pageable pageRequest);

	Optional<Livro> findById(Long id);

	Page<Livro> findByTitulo(String titulo, Pageable pageRequest);
	
	Page<Livro> findByAutor(String autor, Pageable pageRequest);
	
	Optional<Livro> findByIsbn(String isbn);
	
}
