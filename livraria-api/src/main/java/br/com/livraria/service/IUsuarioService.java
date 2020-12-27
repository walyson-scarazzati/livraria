package br.com.livraria.service;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.livraria.model.Role;
import br.com.livraria.model.Usuario;
import net.sf.jasperreports.engine.JRException;

public interface IUsuarioService {
	
	Usuario salvar(Usuario usuario);

	Usuario editar(Usuario usuario);

	void excluir(Usuario usuario);
	
	Optional<Usuario> buscarPorId(Long id);
	
    Page<Usuario> listarUsuarios(Usuario usuario, Pageable pageable);
	
    Page<Usuario> buscarPorNome(String nome, Pageable pageable);
	
    Page<Usuario> buscarPorEmail(String email, Pageable pageable);
    
    List<Role> listarRoles();
    
    String gerarPdfUsuario(OutputStream outputStream) throws JRException, FileNotFoundException;

}

