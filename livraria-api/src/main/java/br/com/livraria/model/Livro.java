package br.com.livraria.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livros")
public class Livro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @NotNull(message = "{livro.isbn.empty}")
	private String isbn;
    
    @NotNull(message = "{livro.autor.empty}")
	private String autor;
    
    @NotNull(message = "{livro.titulo.empty}")
	private String titulo; 
	
    @NotNull(message = "{livro.preco.empty}")
	private Double preco; 
	
    @NotNull(message = "{livro.dataPublicacao.empty}")
    @JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
    private Date dataPublicacao; 
	
    @NotNull(message = "{livro.imagemCapa.empty}")
	private String imagemCapa;
}
