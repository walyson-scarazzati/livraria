package br.com.livraria.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	
    //para colocar esse field como obrigatório no lombok
    @NonNull
	private String nome;
	@Email(message = "Invalid Email")
	@Size(max = 254, message = "It is too big")
	@Column(unique = true)
	@NotNull(message = "Please, set here the user email")
	
    //para colocar esse field como obrigatório no lombok
    @NonNull
	private String email;
	 @NotNull
	 
	  //para colocar esse field como obrigatório no lombok
	 @NonNull
	private String senha;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();
	
}
