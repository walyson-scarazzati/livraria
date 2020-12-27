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
	
	@ApiModelProperty(value = "Código do usuário")
	@NotEmpty(message = "{usuario.id.empty}")
	private Long id;
	
	@ApiModelProperty(value = "Nome do usuário")
	@NotEmpty(message = "{usuario.nome.empty}")
	private String nome;
	
	@ApiModelProperty(value = "Email do usuário")
	@NotEmpty(message = "{usuario.email.empty}")
	private String email;
	
	@ApiModelProperty(value = "Senha do usuário")
	@NotEmpty(message = "{usuario.senha.empty}")
	private String senha;
	
	private RoleDTO role;

}
