package br.com.livraria.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.livraria.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);

	Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

	Page<Usuario> findByEmailContainingIgnoreCase(String email, Pageable pageable);

	@Query(value = "SELECT * FROM usuarios ORDER BY id", countQuery = "SELECT count(*) FROM usuarios", nativeQuery = true)
	Page<Usuario> findAllUsuariosWithPagination(Usuario usuario, Pageable pageable);

	boolean existsByEmail(String email);

	@Query(value = "SELECT  r.id, u.email, u.nome, u.senha, r.descricao FROM  usuarios as u  join usuarios_roles as ur on u.id = ur.usuarios_id join roles as r on r.id = ur.role_id WHERE r.id = :id", nativeQuery = true)
	Page<Usuario> findByRoles(@Param("id") Long id, Pageable pageable);

	@Query(value = "SELECT  r.id, u.email, u.nome, u.senha, r.descricao FROM  usuarios as u  join usuarios_roles as ur on u.id = ur.usuarios_id join roles as r on r.id = ur.role_id", nativeQuery = true)
	List<Usuario> findAllRoles();
}
