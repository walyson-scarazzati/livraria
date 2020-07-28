package br.com.livraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.livraria.model.Role;
import br.com.livraria.model.Usuario;

public interface UsuarioService {
	
	Usuario salvar(Usuario usuario);

	Usuario editar(Usuario usuario);

	void excluir(Usuario usuario);
	
	Optional<Usuario> findById(Long id);
	
    Page<Usuario> findAllUsuarios(Usuario usuario, Pageable pageable);
	
    Page<Usuario> findByNome(String nome, Pageable pageable);
	
    Page<Usuario> findByEmail(String email, Pageable pageable);
    
    List<Role> findAllRoles();

}

