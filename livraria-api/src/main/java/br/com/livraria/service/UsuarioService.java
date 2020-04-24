package br.com.livraria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.livraria.model.Usuario;

public interface UsuarioService {
	
	Usuario salvar(Usuario usuario);

	void editar(Usuario usuario);

	void excluir(Long id);
	
	Usuario findById(Long id);
	
    Page<Usuario> findAllUsuarios(Pageable pageable);
}

