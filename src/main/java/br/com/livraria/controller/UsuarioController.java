package br.com.livraria.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.livraria.model.Usuario;
import br.com.livraria.service.UsuarioServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioServiceImpl usuarioService;

	@GetMapping("/listar")
	public List<Usuario> listar() {
		return usuarioService.findAll();
	}

	@PostMapping(value = "/salvar", 
			produces = {MimeTypeUtils.APPLICATION_JSON_VALUE},
			consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public Usuario salvar(@Valid @RequestBody Usuario usuario) {
		return usuarioService.salvar(usuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizar(@PathVariable(value = "id") Long usuarioId,
			@Valid @RequestBody Usuario usuarioDetails)  {
		Usuario usuario = usuarioService.findById(usuarioId);
		usuario.setId(usuarioDetails.getId());
		usuario.setNome(usuarioDetails.getNome());
		usuario.setEmail(usuarioDetails.getEmail());
		usuario.setSenha(usuarioDetails.getSenha());
		final Usuario updatedUsuario = usuarioService.salvar(usuario);
		return ResponseEntity.ok(updatedUsuario);
	}

	@DeleteMapping("/{id}")
	public Map<String, Boolean> remover(@PathVariable(value = "id") Long usuarioId){
		Usuario usuario = usuarioService.findById(usuarioId);
		if(usuario != null){
			usuarioService.excluir(usuarioId);
        }
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
