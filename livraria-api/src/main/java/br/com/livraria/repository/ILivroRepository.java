package br.com.livraria.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.livraria.model.Livro;

@Repository
public interface ILivroRepository extends JpaRepository<Livro, Long>{
	Page<Livro> findByTituloContainingIgnoreCase(String nome, Pageable pageable);
  
	Page<Livro> findByAutorContainingIgnoreCase(String autor, Pageable pageable);
	
	boolean existsByIsbn(String isbn);
	
	Optional<Livro> findByIsbn(String isbn);
}
