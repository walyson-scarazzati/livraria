package br.com.livraria.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.livraria.exception.BusinessException;
import br.com.livraria.model.Livro;
import br.com.livraria.repository.LivroRepository;

@Service
@Transactional
public class LivroServiceImpl implements LivroService {
	
	private LivroRepository livroRepository;
	
	public LivroServiceImpl(LivroRepository livroRepository) {
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
	public Optional<Livro> findById(Long id) {
		return livroRepository.findById(id);
	}

	@Override
	public Page<Livro> findAll(Livro filter, Pageable pageRequest) {
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
	public Page<Livro> findByTitulo(String nome, Pageable pageable) {
		return livroRepository.findByTituloContainingIgnoreCase(nome, pageable);
	}

	@Override
	public Page<Livro> findByAutor(String autor, Pageable pageable) {
		return livroRepository.findByAutorContainingIgnoreCase(autor, pageable);
	}

	@Override
	public Optional<Livro> findByIsbn(String isbn) {
		return livroRepository.findByIsbn(isbn);
	}

}
