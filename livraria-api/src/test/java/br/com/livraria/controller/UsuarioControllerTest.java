package br.com.livraria.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.livraria.model.Usuario;
import br.com.livraria.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	static String USUARIO_API = "/livraria-api/usuarios";

	@Autowired
	MockMvc mvc;

	@MockBean
	UsuarioServiceImpl usuarioService;

	@Test
	@DisplayName("Deve obter informações de um usuario.")
	public void testFindAllUsuarios() throws Exception {

		// cenário
		Long id = 1l;

		Usuario usuario = Usuario.builder().id(id).nome(createNewUsuario().getNome())
				.email(createNewUsuario().getEmail()).senha(createNewUsuario().getSenha()).build();

		BDDMockito.given(usuarioService.listarUsuarios(Mockito.any(Usuario.class), Mockito.any(Pageable.class)))
				.willReturn(new PageImpl<Usuario>(Arrays.asList(usuario), PageRequest.of(0, 100), 1));

		// execução
		String queryString = String.format("?nome=%s&email=%s&page=0&size=100", usuario.getNome(), usuario.getEmail());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(USUARIO_API.concat(queryString))
				.accept(MediaType.APPLICATION_JSON);

		// verificação
		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("content", Matchers.hasSize(1)))
				.andExpect(jsonPath("totalElements").value(1)).andExpect(jsonPath("pageable.pageSize").value(100))
				.andExpect(jsonPath("pageable.pageNumber").value(0));
	}

	@Test
	@DisplayName("Deve obter informações de um usuario.")
	public void testFindById() throws Exception {

		// cenário ou given
		Long id = 1l;

		Usuario usuario = Usuario.builder().id(id).nome(createNewUsuario().getNome())
				.email(createNewUsuario().getEmail()).senha(createNewUsuario().getSenha()).build();

		BDDMockito.given(usuarioService.buscarPorId(id)).willReturn(Optional.of(usuario));

		// execução ou when
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(USUARIO_API.concat("/" + id))
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("id").value(id))
				.andExpect(jsonPath("nome").value(createNewUsuario().getNome()))
				.andExpect(jsonPath("email").value(createNewUsuario().getEmail()))
				.andExpect(jsonPath("senha").value(createNewUsuario().getSenha()));
	}

	@Test
	@DisplayName("Deve criar um usuario com sucesso")
	public void testSalvar() throws Exception {
		// cenário
		Usuario usuario = createNewUsuario();
		Usuario savedUsuario = Usuario.builder().nome("test").email("test@gmail.com").senha("test").build();

		BDDMockito.given(usuarioService.salvar(Mockito.any(Usuario.class))).willReturn(savedUsuario);

		String json = new ObjectMapper().writeValueAsString(usuario);

		// execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(USUARIO_API.concat("/salvar"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		// verificação
		mvc.perform(request).andExpect(status().isCreated())
				.andExpect(jsonPath("nome").value(createNewUsuario().getNome()))
				.andExpect(jsonPath("email").value(createNewUsuario().getEmail()))
				.andExpect(jsonPath("senha").value(createNewUsuario().getSenha()));

	}

	@Test
	@DisplayName("Deve atualizar um usuario")
	public void testEditar() throws Exception {

		// cenário ou given
		Long id = 1l;
		String json = new ObjectMapper().writeValueAsString(createNewUsuario());
		Usuario updatingUsuario = Usuario.builder().id(1l).nome("editar").email("editar@gmail.com").senha("editar")
				.build();

		BDDMockito.given(usuarioService.buscarPorId(id)).willReturn(Optional.of(updatingUsuario));

		Usuario updatedUsuario = Usuario.builder().id(id).nome("test").email("test@gmail.com").senha("test").build();

		BDDMockito.given(usuarioService.editar(updatingUsuario)).willReturn(updatedUsuario);

		// execução ou when
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(USUARIO_API.concat("/" + 1)).content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("id").value(id))
				.andExpect(jsonPath("nome").value(createNewUsuario().getNome()))
				.andExpect(jsonPath("email").value(createNewUsuario().getEmail()))
				.andExpect(jsonPath("senha").value(createNewUsuario().getSenha()));

	}

	private Usuario createNewUsuario() {
		return Usuario.builder().nome("test").email("test@gmail.com").senha("test").build();
	}

	@Test
	@DisplayName("Deve deletar um usuario")
	public void testExcluir() throws Exception {

		// cenário ou given
		BDDMockito.given(usuarioService.buscarPorId(Mockito.anyLong())).willReturn(
				Optional.of(Usuario.builder().id(1l).nome("test").email("test@gmail.com").senha("test").build()));

		// execução ou when
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(USUARIO_API.concat("/" + 1))
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request).andExpect(status().isNoContent());

	}

	@Test
	@DisplayName("Deve obter informações de um usuario por email.")
	public void testFindByEmail() throws Exception {
		// cenário
		String email = "test@gmail.com";

		Usuario usuario = Usuario.builder().id(1L).nome(createNewUsuario().getNome()).email(email)
				.senha(createNewUsuario().getSenha()).build();

		BDDMockito.given(usuarioService.listarUsuarios(Mockito.any(Usuario.class), Mockito.any(Pageable.class)))
				.willReturn(new PageImpl<Usuario>(Arrays.asList(usuario), PageRequest.of(0, 100), 1));

		// execução
		String queryString = String.format("?nome=%s&email=%s&page=0&size=100", usuario.getNome(), usuario.getEmail());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(USUARIO_API.concat(queryString))
				.accept(MediaType.APPLICATION_JSON);

		// verificação
		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("content", Matchers.hasSize(1)))
				.andExpect(jsonPath("totalElements").value(1)).andExpect(jsonPath("pageable.pageSize").value(100))
				.andExpect(jsonPath("pageable.pageNumber").value(0));

	}

	@Test
	@DisplayName("Deve obter informações de um usuario por nome.")
	public void testFindByNome() throws Exception {

		// cenário
		String nome = "test";

		Usuario usuario = Usuario.builder().id(1L).nome(nome).email(createNewUsuario().getEmail())
				.senha(createNewUsuario().getSenha()).build();

		BDDMockito.given(usuarioService.listarUsuarios(Mockito.any(Usuario.class), Mockito.any(Pageable.class)))
				.willReturn(new PageImpl<Usuario>(Arrays.asList(usuario), PageRequest.of(0, 100), 1));

		// execução
		String queryString = String.format("?nome=%s&email=%s&page=0&size=100", usuario.getNome(), usuario.getEmail());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(USUARIO_API.concat(queryString))
				.accept(MediaType.APPLICATION_JSON);

		// verificação
		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("content", Matchers.hasSize(1)))
				.andExpect(jsonPath("totalElements").value(1)).andExpect(jsonPath("pageable.pageSize").value(100))
				.andExpect(jsonPath("pageable.pageNumber").value(0));

	}

}
