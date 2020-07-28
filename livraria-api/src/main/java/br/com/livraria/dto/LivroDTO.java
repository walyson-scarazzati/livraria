package br.com.livraria.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

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
public class LivroDTO {

	private Long id;

	@NotEmpty(message = "{livro.isbn.empty}")
	private String isbn;

	@NotEmpty(message = "{livro.autor.empty}")
	private String autor;

	@NotEmpty(message = "{livro.titulo.empty}")
	private String titulo;

	@NotEmpty(message = "{livro.preco.empty}")
	private Double preco;

	@NotEmpty(message = "{livro.dataPublicacao.empty}")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private Date dataPublicacao;

	@NotEmpty(message = "{livro.imagemCapa.empty}")
	private String imagemCapa;

}
