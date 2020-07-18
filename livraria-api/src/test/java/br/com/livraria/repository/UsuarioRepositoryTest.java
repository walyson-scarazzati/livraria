package br.com.livraria.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.livraria.model.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class UsuarioRepositoryTest {

	// Utilizando para criar um cenário
	@Autowired
	TestEntityManager entityManager;

	@Autowired
	UsuarioRepository repository;

	@Test
	@DisplayName("Deve retornar verdadeiro quando existir um email na base")
	public void returnTrueWhenEmailExists() {
		// cenário
		String email = "test@gmail.com";
		Usuario usuario = createNewUsuario(email);
		entityManager.persist(usuario);

		// execução
		boolean exists = repository.existsByEmail(email);

		// verificação
		assertThat(exists).isTrue();
	}

	@Test
	@DisplayName("Deve retornar verdadeiro quando não existir um email na base")
	public void returnFalseWhenEmailDoesntExist() {
		// cenário
		String email = "test3@gmail.com";

		// execução
		boolean exists = repository.existsByEmail(email);

		// verificação
		assertThat(exists).isFalse();
	}

	@Test
	@DisplayName("Deve obter um livro por id.")
	public void findById() {
		// cenário
		Usuario usuario = createNewUsuario("123");
		entityManager.persist(usuario);

		// execução
		Optional<Usuario> foundLivro = repository.findById(usuario.getId());

		// verificação
		assertThat(foundLivro.isPresent()).isTrue();
	}

	@Test
	@DisplayName("Deve salvar um usuario.")
	public void saveUsuarioTest(){
		// cenário
		Usuario usuario = createNewUsuario("123");

		// execução
		Usuario savedUsuario = repository.save(usuario);

		// verificação
		assertThat(savedUsuario.getId()).isNotNull();
	}
	
	@Test
	@DisplayName("Deve editar um usuario.")
	public void editUsuarioTest(){
		// cenário
		Usuario usuario = createNewUsuario("123");

		// execução
		Usuario savedUsuario = repository.save(usuario);

		// verificação
		assertThat(savedUsuario.getId()).isNotNull();
	}

	@Test
	@DisplayName("Deve deletar um usuario.")
	public void deleteUsuarioTest(){
		// cenário
		Usuario usuario = createNewUsuario("123");
		entityManager.persist(usuario);

		Usuario foundUsuario = entityManager.find(Usuario.class, usuario.getId());

		// execução
		repository.delete(foundUsuario);

		// verificação
		Usuario deletedUsuario = entityManager.find(Usuario.class, usuario.getId());
		assertThat(deletedUsuario).isNull();

	}

	public static Usuario createNewUsuario(String email) {
		return Usuario.builder().nome("test").senha("test").email(email).build();
	}

}
