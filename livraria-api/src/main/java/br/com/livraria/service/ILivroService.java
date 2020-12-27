package br.com.livraria.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.livraria.model.Livro;

public interface ILivroService {
	
	Livro salvar(Livro livro);

	Livro editar(Livro livro);

	void excluir(Livro livro);

	Page<Livro> listarLivros(Livro filter, Pageable pageRequest);

	Optional<Livro> buscarPorId(Long id);

	Page<Livro> buscarPorTitulo(String titulo, Pageable pageRequest);
	
	Page<Livro> buscarPorAutor(String autor, Pageable pageRequest);
	
	Optional<Livro> buscarPorIsbn(String isbn);
	
}
