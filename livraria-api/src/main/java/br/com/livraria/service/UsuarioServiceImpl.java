package br.com.livraria.service;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.livraria.config.ReportCreationManager;
import br.com.livraria.model.Role;
import br.com.livraria.model.Usuario;
import br.com.livraria.repository.IRoleRepository;
import br.com.livraria.repository.IUsuarioRepository;
import net.sf.jasperreports.engine.JRException;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {

	private IUsuarioRepository usuarioRepository;

	private IRoleRepository roleRepository;

	public UsuarioServiceImpl(IUsuarioRepository usuarioRepository, IRoleRepository roleRepository) {
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
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
	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Page<Usuario> listarUsuarios(Usuario usuario, Pageable pageable) {
		return usuarioRepository.findAllUsuariosWithPagination(usuario, pageable);
	}

	@Override
	public Page<Usuario> buscarPorNome(String nome, Pageable pageable) {
		return usuarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
	}

	@Override
	public Page<Usuario> buscarPorEmail(String email, Pageable pageable) {
		return usuarioRepository.findByEmailContainingIgnoreCase(email, pageable);
	}

	@Override
	public List<Role> listarRoles() {
		return roleRepository.findAll();
	}

	public String gerarPdfUsuario(OutputStream outputStream) throws JRException, FileNotFoundException {
		List<Usuario> usuarioList = (List<Usuario>) usuarioRepository.findAll();
		return ReportCreationManager.generateReport(usuarioList, outputStream);

	}
}
