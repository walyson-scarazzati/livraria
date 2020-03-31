package br.com.livraria.service;

import java.util.List;

import br.com.livraria.model.Livro;

public interface LivroService {
	Livro salvar(Livro livraria);

	void editar(Livro livraria);

	void excluir(Long id);

	List<Livro> findAll();

	Livro findById(Long id);

	List<Livro> findByTitulo(String nome);
}
