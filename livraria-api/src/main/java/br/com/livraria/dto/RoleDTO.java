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
public class RoleDTO {
	
	@ApiModelProperty(value = "${role.id}")
	private Long id;
	
	@ApiModelProperty(value = "${role.descricao}")
	@NotEmpty(message = "{role.descricao.empty}")
	private String descricao;

}
