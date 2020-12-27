package br.com.livraria.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {

	@ApiModelProperty(value = "Código do livro")
	@NotEmpty(message = "{livro.id.empty}")
	private Long id;

	@ApiModelProperty(value = "Isbn do livro")
	@NotEmpty(message = "{livro.isbn.empty}")
	private String isbn;

	@ApiModelProperty(value = "Autor do livro")
	@NotEmpty(message = "{livro.autor.empty}")
	private String autor;

	@ApiModelProperty(value = "Título do livro")
	@NotEmpty(message = "{livro.titulo.empty}")
	private String titulo;

	@ApiModelProperty(value = "Preço do livro")
	@NotEmpty(message = "{livro.preco.empty}")
	private Double preco;

	@ApiModelProperty(value = "Data da publicação do livro")
	@NotEmpty(message = "{livro.dataPublicacao.empty}")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private Date dataPublicacao;

	@ApiModelProperty(value = "Imagem da capa do livro")
	@NotEmpty(message = "{livro.imagemCapa.empty}")
	private String imagemCapa;

}
