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
public class RoleDTO {
	
	private Long id;
	
	@NotEmpty(message = "{role.descricao.empty}")
	private String descricao;

}
