package br.com.livraria.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role  implements Serializable {
	
	private static final long serialVersionUID = 13L;
	
	@Id
	private String nome;

	public Role() {
	}
	
	public Role(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
