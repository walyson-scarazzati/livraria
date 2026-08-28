package br.com.livraria.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.livraria.model.Role;

public interface IRoleService {

	Role salvar(Role role);

	Role editar(Role role);

	void excluir(Role role);

	Optional<Role> buscarPorId(Long id);

	Page<Role> listarRoles(Role role, Pageable pageable);

}
