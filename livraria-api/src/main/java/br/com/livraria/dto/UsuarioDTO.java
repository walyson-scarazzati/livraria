package br.com.livraria.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	
	@ApiModelProperty(value = "${usuario.id}")
	private Long id;
	
	@ApiModelProperty(value = "${usuario.nome}")
	@NotEmpty(message = "{usuario.nome.empty}")
	private String nome;
	
	@ApiModelProperty(value = "${usuario.email}")
	@NotEmpty(message = "{usuario.email.empty}")
	private String email;
	
	@ApiModelProperty(value = "${usuario.senha}")
	@NotEmpty(message = "{usuario.senha.empty}")
	private String senha;
	
	private RoleDTO role;

}
