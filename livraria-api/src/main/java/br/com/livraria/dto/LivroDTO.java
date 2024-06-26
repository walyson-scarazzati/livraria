package br.com.livraria.dto;



import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

	@ApiModelProperty(value = "${livro.id}")
	private Long id;

	@ApiModelProperty(value = "${livro.isbn}")
	@NotEmpty(message = "{livro.isbn.empty}")
	private String isbn;

	@ApiModelProperty(value = "${livro.autor}")
	@NotEmpty(message = "{livro.autor.empty}")
	private String autor;

	@ApiModelProperty(value = "${livro.titulo}")
	@NotEmpty(message = "{livro.titulo.empty}")
	private String titulo;

	@ApiModelProperty(value = "${livro.preco}")
	@NotNull(message = "{livro.preco.empty}")
	private Double preco;

	@ApiModelProperty(value = "${livro.dataPublicacao}")
	@NotNull(message = "{livro.dataPublicacao.empty}")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@Temporal(TemporalType.DATE)
	private Date dataPublicacao;

	@ApiModelProperty(value = "${livro.imagemCapa}")
	@NotEmpty(message = "{livro.imagemCapa.empty}")
	private String imagemCapa;

}
