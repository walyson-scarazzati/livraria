package br.com.livraria.service.impl;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.livraria.config.ReportCreationManager;
import br.com.livraria.exception.BusinessException;
import br.com.livraria.model.Livro;
import br.com.livraria.repository.ILivroRepository;
import br.com.livraria.service.ILivroService;
import net.sf.jasperreports.engine.JRException;

@Service
@Transactional
public class LivroServiceImpl implements ILivroService {
	
	private ILivroRepository livroRepository;
	
	public LivroServiceImpl(ILivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }
	
	@Override
	public Livro salvar(Livro livro) {
		if (livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new BusinessException("Isbn já cadastrado.");
        }
		return livroRepository.save(livro);
	}

	@Override
	public Livro editar(Livro livro) {
		 if (livro == null || livro.getId() == null) {
	            throw new IllegalArgumentException("Livro id cant be null");
	        }
		return livroRepository.save(livro); 
	}

	@Override
	public void excluir(Livro livro) {
		livroRepository.delete(livro);
	}

	@Override
	public Optional<Livro> buscarPorId(Long id) {
		return livroRepository.findById(id);
	}

	@Override
	public Page<Livro> listarLivros(Livro filter, Pageable pageRequest) {
		 Example<Livro> example = Example.of(filter,
	                ExampleMatcher
	                        .matching()
	                        .withIgnoreCase()
	                        .withIgnoreNullValues()
	                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
	        );

	        return livroRepository.findAll(example, pageRequest);
	}

	@Override
	public Page<Livro> buscarPorTitulo(String nome, Pageable pageable) {
		return livroRepository.findByTituloContainingIgnoreCase(nome, pageable);
	}

	@Override
	public Page<Livro> buscarPorAutor(String autor, Pageable pageable) {
		return livroRepository.findByAutorContainingIgnoreCase(autor, pageable);
	}

	@Override
	public Optional<Livro> buscarPorIsbn(String isbn) {
		return livroRepository.findByIsbn(isbn);
	}
	
	@Override
	public String gerarPdfLivro(OutputStream outputStream) throws JRException, FileNotFoundException {
		List<Livro> livroList = (List<Livro>) livroRepository.findAll();
		return ReportCreationManager.generateReportLivro(livroList, outputStream);

	}

}
