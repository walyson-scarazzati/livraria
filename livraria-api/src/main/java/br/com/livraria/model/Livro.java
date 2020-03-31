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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
@Entity
@Table(name = "livros")
public class Livro implements Serializable {
	
	private static final long serialVersionUID = -3465813074586302847L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long Isbn;
    
    @NotNull
	private String autor;
    
    @NotNull
	private String titulo; 
	
    @NotNull
	private Double preco; 
	
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
    private Date dataPublicacao; 
	
    @NotNull
	private String imagemCapa;
}
