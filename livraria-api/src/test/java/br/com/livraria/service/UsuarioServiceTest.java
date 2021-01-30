package br.com.livraria.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.livraria.exception.BusinessException;
import br.com.livraria.model.Livro;
import br.com.livraria.repository.ILivroRepository;
import br.com.livraria.service.impl.LivroServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	ILivroService service;

	@MockBean
	ILivroRepository repository;

	@BeforeEach
	public void setUp() {
		this.service = new LivroServiceImpl(repository);
	}

	@Test
	@DisplayName("Deve salvar um livro")
	public void saveLivroTest() {
		Date date = new Date();

		// cenário
		Livro livro = createValidLivro();
		// mocando
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
		Mockito.when(repository.save(livro)).thenReturn(Livro.builder().titulo("As aventuras").autor("Fulano")
				.preco(2.30).dataPublicacao(date).imagemCapa("1").isbn("123").id(1l).build());

		// execução
		Livro savedLivro = service.salvar(livro);

		// verificação
		assertThat(savedLivro.getId()).isNotNull();
		assertThat(savedLivro.getIsbn()).isEqualTo("123");
		assertThat(savedLivro.getTitulo()).isEqualTo("As aventuras");
		assertThat(savedLivro.getAutor()).isEqualTo("Fulano");
	}

	@Test
	@DisplayName("Deve lançar erro de negócio ao tentar salvar um livro com isbn duplicado")
	public void shouldNotSaveALivroWithDuplicatedISBN() {
		// cenário
		Livro livro = createValidLivro();
		// mocando
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);

		// execução
		Throwable exception = Assertions.catchThrowable(() -> service.salvar(livro));

		// verificação
		assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("Isbn já cadastrado.");

		// para verificar que o método nunca será executado
		Mockito.verify(repository, Mockito.never()).save(livro);

	}

	@Test
	@DisplayName("Deve obter um livro por ID")
	public void getByIdTest() {
		// cenário
		Long id = 1l;

		Livro livro = createValidLivro();
		livro.setId(id);
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(livro));

		// execução
		Optional<Livro> foundLivro = service.buscarPorId(id);

		// verificação
		assertThat(foundLivro.isPresent()).isTrue();
		assertThat(foundLivro.get().getId()).isEqualTo(id);
		assertThat(foundLivro.get().getAutor()).isEqualTo(livro.getAutor());
		assertThat(foundLivro.get().getTitulo()).isEqualTo(livro.getTitulo());
		assertThat(foundLivro.get().getIsbn()).isEqualTo(livro.getIsbn());
	}

	@Test
	@DisplayName("Deve retornar vazio ao obter um livro por ID quando ele não existir na base")
	public void livroNotFoundByIdTest() {
		// cenário
		Long id = 1l;

		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

		// execução
		Optional<Livro> livro = service.buscarPorId(id);

		// verificação
		assertThat(livro.isPresent()).isFalse();
	}

	@Test
	@DisplayName("Deve deletar um livro")
	public void deleteLivroTest() {
		Date date = new Date();

		// cenário
		Livro livro = Livro.builder().titulo("As aventuras").autor("test").preco(2.30).dataPublicacao(date)
				.imagemCapa("1").isbn("1234").id(2l).build();

		// execução
		assertDoesNotThrow(() -> service.excluir(livro));

		// verificação
		Mockito.verify(repository, Mockito.times(1)).deleteById(livro.getId());

	}

	@Test
	@DisplayName("Deve ocorrer um erro ao tentar atualizar um livro inexistente")
	public void updateInvalidLivroTest() {
		// cenário
		Livro livro = new Livro();

		// execução
		assertThrows(IllegalArgumentException.class, () -> service.editar(livro));

		// verificação
		Mockito.verify(repository, Mockito.never()).save(livro);

	}

	@Test
	@DisplayName("Deve ocorrer um erro ao tentar deletar um livro inexistente")
	public void deleteInvalidLivroTest() {
		// cenário
		Livro livro = new Livro();

		// execução
		assertThrows(IllegalArgumentException.class, () -> service.excluir(livro));

		// verificação
		Mockito.verify(repository, Mockito.never()).delete(livro);

	}

	@Test
	@DisplayName("Deve atualizar um livro")
	public void updateLivroTest() {
		Date date = new Date();

		// cenário
		Long id = 1l;
		Livro updatingLivro = Livro.builder().titulo("As aventuras").autor("Fulano").preco(2.30).dataPublicacao(date)
				.imagemCapa("1").isbn("123").id(id).build();

		Livro updatedLivro = createValidLivro();
		updatedLivro.setId(id);

		Mockito.when(repository.save(updatedLivro)).thenReturn(updatedLivro);

		// execução
		Livro livro = service.editar(updatedLivro);

		// verificação
		assertThat(livro.getId()).isEqualTo(updatedLivro.getId());
		assertThat(livro.getAutor()).isEqualTo(updatedLivro.getAutor());
		assertThat(livro.getTitulo()).isEqualTo(updatedLivro.getTitulo());
		assertThat(livro.getIsbn()).isEqualTo(updatedLivro.getIsbn());
	}

	@Test
	@DisplayName("Deve filtrar livros pelas propriedades")
	public void findLivroTest() {
		// cenário
		Livro livro = createValidLivro();

		PageRequest pageRequest = PageRequest.of(0, 10);

		List<Livro> lista = Arrays.asList(livro);
		Page<Livro> page = new PageImpl<Livro>(lista, pageRequest, 1);
		Mockito.when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class))).thenReturn(page);

		// execução
		Page<Livro> result = service.listarLivros(livro, pageRequest);

		// verificação
		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat(result.getContent()).isEqualTo(lista);
		assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
		assertThat(result.getPageable().getPageSize()).isEqualTo(10);

	}

	@Test
	@DisplayName("deve obter um livro pelo isbn")
	public void getLivroByIsbnTest() {
		// cenário
		String isbn = "123";
		Date date = new Date();

		Mockito.when(repository.findByIsbn(isbn)).thenReturn(Optional.of(Livro.builder().titulo("As aventuras")
				.autor("Fulano").preco(2.30).dataPublicacao(date).imagemCapa("1").isbn(isbn).id(1l).build()));

		// execução
		Optional<Livro> livro = service.buscarPorIsbn(isbn);

		// verificação
		assertThat(livro.isPresent()).isTrue();
		assertThat(livro.get().getId()).isEqualTo(1l);
		assertThat(livro.get().getIsbn()).isEqualTo(isbn);

		Mockito.verify(repository, Mockito.times(1)).findByIsbn(isbn);
	}

	private Livro createValidLivro() {
		Date date = new Date();
		return Livro.builder().titulo("Aventuras").autor("Fulano").preco(2.30).dataPublicacao(date).imagemCapa("1")
				.isbn("123").build();
	}

}
