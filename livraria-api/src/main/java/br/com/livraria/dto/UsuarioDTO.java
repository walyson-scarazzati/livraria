package br.com.livraria.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
	
	private Long id;
	
	@NotEmpty(message = "{usuario.nome.empty}")
	private String nome;
	
	@NotEmpty(message = "{usuario.email.empty}")
	private String email;
	
	@NotEmpty(message = "{usuario.senha.empty}")
	private String senha;

}
