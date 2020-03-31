package br.com.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.livraria.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{
	List<Livro> findByTitulo(String nome);
  
}
