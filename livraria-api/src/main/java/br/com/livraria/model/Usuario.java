package br.com.livraria.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "{usuario.nome.empty}")

	// para colocar esse field como obrigatório no lombok
	//@NonNull
	private String nome;
	
	@Email(message = "{usuario.email.invalido}")
	@Size(max = 254, message = "{usuario.email.size}")
	@Column(unique = true)
	@NotNull(message = "{usuario.email.empty}")

	// para colocar esse field como obrigatório no lombok
	//@NonNull
	private String email;

	@NotNull(message = "{usuario.senha.empty}")

	// para colocar esse field como obrigatório no lombok
	//@NonNull
	@JsonIgnore
	private String senha;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Role role;

}
