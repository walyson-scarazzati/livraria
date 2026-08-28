package br.com.livraria.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.livraria.model.Role;
import br.com.livraria.repository.IRoleRepository;
import br.com.livraria.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	private IRoleRepository roleRepository;

	public RoleServiceImpl(IRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role salvar(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role editar(Role role) {
		return roleRepository.saveAndFlush(role);
	}

	@Override
	public void excluir(Role role) {
		roleRepository.delete(role);
	}

	@Override
	public Optional<Role> buscarPorId(Long id) {
		return roleRepository.findById(id);
	}

	@Override
	public Page<Role> listarRoles(Role role, Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

}
