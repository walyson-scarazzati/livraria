package br.com.livraria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.livraria.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByEmail(String username);

	
	@Query(value = "SELECT * FROM usuarios ORDER BY id", 
		   countQuery = "SELECT count(*) FROM usuarios", 
		   nativeQuery = true)
	Page<Usuario> findAllUsuariosWithPagination(Pageable pageable);
}
