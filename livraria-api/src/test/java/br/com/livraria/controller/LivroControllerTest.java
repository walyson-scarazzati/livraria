package br.com.livraria.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
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

import br.com.livraria.model.Livro;
import br.com.livraria.service.LivroServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = LivroController.class)
@AutoConfigureMockMvc
public class LivroControllerTest {

	static String LIVRO_API = "/livraria-api/livros/";

	@Autowired
	MockMvc mvc;

	@MockBean
	LivroServiceImpl livroService;

	@Test
	@DisplayName("Deve obter informações de um livro.")
	public void testFindAllLivros() throws Exception {
		// cenário
		Long id = 1l;

		Livro livro = Livro.builder().id(id).titulo(createNewLivro().getTitulo()).autor(createNewLivro().getAutor())
				.isbn(createNewLivro().getIsbn()).build();

		BDDMockito.given(livroService.findAll(Mockito.any(Livro.class), Mockito.any(Pageable.class)))
				.willReturn(new PageImpl<Livro>(Arrays.asList(livro), PageRequest.of(0, 100), 1));

		// execução
		String queryString = String.format("?titulo=%s&autor=%s&page=0&size=100", livro.getTitulo(), livro.getAutor());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(LIVRO_API.concat(queryString))
				.accept(MediaType.APPLICATION_JSON);

		// verificação
		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("content", Matchers.hasSize(1)))
				.andExpect(jsonPath("totalElements").value(1)).andExpect(jsonPath("pageable.pageSize").value(100))
				.andExpect(jsonPath("pageable.pageNumber").value(0));
	}

	@Test
	@DisplayName("Deve obter informações de um livro por ISBN.")
	public void testLivroByIsbn() throws Exception {

		// cenário ou given
		String isbn = "123";

		Livro livro = Livro.builder().id(1L).titulo(createNewLivro().getTitulo()).autor(createNewLivro().getAutor())
				.preco(createNewLivro().getPreco()).dataPublicacao(createNewLivro().getDataPublicacao())
				.imagemCapa(createNewLivro().getImagemCapa()).isbn(createNewLivro().getIsbn()).build();

		BDDMockito.given(livroService.findByIsbn(isbn)).willReturn(Optional.of(livro));

		// execução ou when
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(LIVRO_API.concat("/" + isbn))
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("id").value(1L))
				.andExpect(jsonPath("titulo").value(livro.getTitulo()))
				.andExpect(jsonPath("autor").value(livro.getAutor()))
				.andExpect(jsonPath("preco").value(livro.getPreco()))
				.andExpect(jsonPath("dataPublicacao").value(livro.getDataPublicacao()))
				.andExpect(jsonPath("imagemCapa").value(livro.getImagemCapa()))
				.andExpect(jsonPath("isbn").value(livro.getIsbn()));

	}

	private Livro createNewLivro() {
		Date date = new Date();
		return Livro.builder().titulo("As aventuras").autor("Fulano").preco(2.30).dataPublicacao(date).imagemCapa("1")
				.isbn("123").id(1l).build();
	}

	@Test
	@DisplayName("Deve criar um livro com sucesso")
	public void testSalvar() throws Exception {
		Date date = new Date();

		// cenário
		Livro livro = createNewLivro();
		Livro savedLivro = Livro.builder().id(10l).titulo("Aventuras").autor("Fulano").preco(2.30).dataPublicacao(date)
				.imagemCapa("1").isbn("001").build();

		BDDMockito.given(livroService.salvar(Mockito.any(Livro.class))).willReturn(savedLivro);

		String json = new ObjectMapper().writeValueAsString(livro);

		// execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LIVRO_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		// verificação
		mvc.perform(request).andExpect(status().isCreated()).andExpect(jsonPath("id").value(10l))
				.andExpect(jsonPath("titulo").value(livro.getTitulo()))
				.andExpect(jsonPath("autor").value(livro.getAutor()))
				.andExpect(jsonPath("preco").value(livro.getPreco()))
				.andExpect(jsonPath("dataPublicacao").value(livro.getDataPublicacao()))
				.andExpect(jsonPath("imagemCapa").value(livro.getImagemCapa()))
				.andExpect(jsonPath("isbn").value(livro.getIsbn()));
	}

	@Test
	@DisplayName("Deve atualizar um livro")
	public void testEditar() throws Exception {
		Date date = new Date();

		// cenário ou given
		Long id = 1l;
		String json = new ObjectMapper().writeValueAsString(createNewLivro());
		Livro updatingLivro = Livro.builder().id(1l).titulo("some title").autor("some author").preco(3.5)
				.dataPublicacao(date).imagemCapa("1234").isbn("321").build();

		BDDMockito.given(livroService.findById(id)).willReturn(Optional.of(updatingLivro));

		Livro updatedLivro = Livro.builder().id(id).autor("Robson").titulo("As aventuras").isbn("321").build();

		BDDMockito.given(livroService.editar(updatingLivro)).willReturn(updatedLivro);

		// execução ou when
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(LIVRO_API.concat("/" + 1)).content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("id").value(id))
				.andExpect(jsonPath("titulo").value(createNewLivro().getTitulo()))
				.andExpect(jsonPath("autor").value(createNewLivro().getAutor()))
				.andExpect(jsonPath("preco").value(createNewLivro().getPreco()))
				.andExpect(jsonPath("dataPublicacao").value(createNewLivro().getDataPublicacao()))
				.andExpect(jsonPath("imagemCapa").value(createNewLivro().getImagemCapa()))
				.andExpect(jsonPath("isbn").value("321"));
	}

	@Test
	@DisplayName("Deve deletar um livro")
	public void testExcluir() throws Exception {

		// cenário ou given
		BDDMockito.given(livroService.findById(Mockito.anyLong()))
				.willReturn(Optional.of(Livro.builder().id(1l).build()));

		// execução ou when
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(LIVRO_API.concat("/" + 1))
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request).andExpect(status().isNoContent());

	}

	@Test
	@DisplayName("Deve obter informações de um livro por titulo.")
	public void testFindByTitulo() throws Exception {
		Date date = new Date();

		// cenário
		String titulo = "test";

		Livro livro = Livro.builder().id(1l).titulo(titulo).autor("some author").preco(3.5).dataPublicacao(date)
				.imagemCapa("1234").isbn("321").build();

		BDDMockito.given(livroService.findAll(Mockito.any(Livro.class), Mockito.any(Pageable.class)))
				.willReturn(new PageImpl<Livro>(Arrays.asList(livro), PageRequest.of(0, 100), 1));

		// execução
		String queryString = String.format("?titulo=%s&autor=%s&page=0&size=100", livro.getTitulo(), livro.getAutor());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(LIVRO_API.concat(queryString))
				.accept(MediaType.APPLICATION_JSON);

		// verificação
		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("content", Matchers.hasSize(1)))
				.andExpect(jsonPath("totalElements").value(1)).andExpect(jsonPath("pageable.pageSize").value(100))
				.andExpect(jsonPath("pageable.pageNumber").value(0));
	}

	@Test
	@DisplayName("Deve obter informações de um livro por autor.")
	public void testFindByAutor() throws Exception {
		Date date = new Date();

		// cenário
		String autor = "test";

		Livro livro = Livro.builder().id(1l).titulo("titulo").autor(autor).preco(3.5).dataPublicacao(date)
				.imagemCapa("1234").isbn("321").build();

		BDDMockito.given(livroService.findAll(Mockito.any(Livro.class), Mockito.any(Pageable.class)))
				.willReturn(new PageImpl<Livro>(Arrays.asList(livro), PageRequest.of(0, 100), 1));

		// execução
		String queryString = String.format("?titulo=%s&autor=%s&page=0&size=100", livro.getTitulo(), livro.getAutor());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(LIVRO_API.concat(queryString))
				.accept(MediaType.APPLICATION_JSON);

		// verificação
		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("content", Matchers.hasSize(1)))
				.andExpect(jsonPath("totalElements").value(1)).andExpect(jsonPath("pageable.pageSize").value(100))
				.andExpect(jsonPath("pageable.pageNumber").value(0));
	}

}
