package br.com.livraria.service;

import java.util.List;

import br.com.livraria.model.Usuario;

public interface UsuarioService {
	
	Usuario salvar(Usuario usuario);

	void editar(Usuario usuario);

	void excluir(Long id);
	
	Usuario findById(Long id);

	List<Usuario> findAll();
}
