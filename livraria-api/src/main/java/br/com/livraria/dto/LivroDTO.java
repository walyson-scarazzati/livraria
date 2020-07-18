package br.com.livraria.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

	private Long id;

	@NotNull
	private String isbn;

	@NotNull

	// para colocar esse field como obrigatório no lombok
	@NonNull
	private String autor;

	@NotNull

	// para colocar esse field como obrigatório no lombok
	@NonNull
	private String titulo;

	@NotNull

	// para colocar esse field como obrigatório no lombok
	@NonNull
	private Double preco;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)

	// para colocar esse field como obrigatório no lombok
	@NonNull
	private Date dataPublicacao;

	@NotNull

	// para colocar esse field como obrigatório no lombok
	@NonNull
	private String imagemCapa;

}
