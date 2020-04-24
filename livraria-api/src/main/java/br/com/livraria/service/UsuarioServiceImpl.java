package br.com.livraria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.livraria.model.Usuario;
import br.com.livraria.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
    private UsuarioRepository usuarioDao;

	@Override
	public Usuario salvar(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public void editar(Usuario usuario) {
		usuarioDao.saveAndFlush(usuario); 
	}

	@Override
	public void excluir(Long id) {
		usuarioDao.deleteById(id); 
	}
	
	@Override
	public Usuario findById(Long id) {
		return usuarioDao.getOne(id);
	}

	@Override
	public Page<Usuario> findAllUsuarios(Pageable pageable) {
		return usuarioDao.findAllUsuariosWithPagination(pageable);
	}

}
