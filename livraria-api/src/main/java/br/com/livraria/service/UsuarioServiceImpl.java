package br.com.livraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.livraria.model.Usuario;
import br.com.livraria.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario editar(Usuario usuario) {
		return usuarioRepository.saveAndFlush(usuario);
	}

	@Override
	public void excluir(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Page<Usuario> findAllUsuarios(Usuario usuario, Pageable pageable) {
		return usuarioRepository.findAllUsuariosWithPagination(usuario, pageable);
	}

	@Override
	public Page<Usuario> findByNome(String nome, Pageable pageable) {
		return usuarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
	}

	@Override
	public Page<Usuario> findByEmail(String email, Pageable pageable) {
		return usuarioRepository.findByEmailContainingIgnoreCase(email, pageable);
	}

	@Override
	public List<Usuario> findAllRoles() {
		return usuarioRepository.findAllRoles();
	}

	@Override
	public Page<Usuario> findByRoles(Long id, Pageable pageable) {
		return usuarioRepository.findByRoles(id, pageable);
	}
}
