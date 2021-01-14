package br.com.livraria.service;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.livraria.model.Livro;
import net.sf.jasperreports.engine.JRException;

public interface ILivroService {
	
	Livro salvar(Livro livro);

	Livro editar(Livro livro);

	void excluir(Livro livro);

	Page<Livro> listarLivros(Livro filter, Pageable pageRequest);

	Optional<Livro> buscarPorId(Long id);

	Page<Livro> buscarPorTitulo(String titulo, Pageable pageRequest);
	
	Page<Livro> buscarPorAutor(String autor, Pageable pageRequest);
	
	Optional<Livro> buscarPorIsbn(String isbn);
	
    String gerarPdfLivro(OutputStream outputStream) throws JRException, FileNotFoundException;
	
}
