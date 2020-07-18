package br.com.livraria.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.livraria.model.Livro;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LivroRepositoryTest {

	// Utilizando para criar um cenário
	@Autowired
	TestEntityManager entityManager;

	@Autowired
	LivroRepository repository;

	@Test
	@DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado")
	public void returnTrueWhenIsbnExists() {
		// cenário
		String isbn = "123";
		Livro livro = createNewLivro(isbn);
		entityManager.persist(livro);

		// execução
		boolean exists = repository.existsByIsbn(isbn);

		// verificação
		assertThat(exists).isTrue();
	}

	@Test
	@DisplayName("Deve retornar verdadeiro quando não existir um livro na base com isbn informado")
	public void returnFalseWhenIsbnDoesntExist() {
		// cenário
		String isbn = "123";

		// execução
		boolean exists = repository.existsByIsbn(isbn);

		// verificação
		assertThat(exists).isFalse();
	}

	@Test
	@DisplayName("Deve obter um livro por id.")
	public void findById() {
		// cenário
		Livro livro = createNewLivro("123");
		entityManager.persist(livro);

		// execução
		Optional<Livro> foundLivro = repository.findById(livro.getId());

		// verificação
		assertThat(foundLivro.isPresent()).isTrue();
	}

	@Test
	@DisplayName("Deve salvar um livro.")
	public void savelivroTest() {
		// cenário
		Livro livro = createNewLivro("123");

		// execução
		Livro savedLivro = repository.save(livro);

		// verificação
		assertThat(savedLivro.getId()).isNotNull();
	}
	
	@Test
	@DisplayName("Deve editar um livro.")
	public void editlivroTest() {
		// cenário
		Livro livro = createNewLivro("123");

		// execução
		Livro savedLivro = repository.save(livro);

		// verificação
		assertThat(savedLivro.getId()).isNotNull();
	}

	@Test
	@DisplayName("Deve deletar um livro.")
	public void deletelivroTest() {
		// cenário
		Livro livro = createNewLivro("123");
		entityManager.persist(livro);

		Livro foundLivro = entityManager.find(Livro.class, livro.getId());

		// execução
		repository.delete(foundLivro);

		// verificação
		Livro deletedLivro = entityManager.find(Livro.class, livro.getId());
		assertThat(deletedLivro).isNull();

	}

	public static Livro createNewLivro(String isbn) {
		Date date = new Date();
		return Livro.builder().titulo("Aventuras").autor("Fulano").preco(2.30).dataPublicacao(date).imagemCapa("1")
				.isbn(isbn).build();
	}

}
