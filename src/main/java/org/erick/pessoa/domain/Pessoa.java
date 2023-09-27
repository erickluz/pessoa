package org.erick.pessoa.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private LocalDateTime dataNascimento;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idEndereco")
	private Endereco enderecoPrincipal;
	
	public Pessoa() {
		
	}
	
	public Pessoa(Long id, String nome, LocalDateTime dataNascimento, Endereco enderecoPrincipal) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.enderecoPrincipal = enderecoPrincipal;
	}

	public Pessoa(String nome, LocalDateTime dataNascimento, Endereco enderecoPrincipal) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.enderecoPrincipal = enderecoPrincipal;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDateTime getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Endereco getEnderecoPrincipal() {
		return enderecoPrincipal;
	}
	public void setEnderecoPrincipal(Endereco enderecoPrincipal) {
		this.enderecoPrincipal = enderecoPrincipal;
	}


	@Override
	public int hashCode() {
		return Objects.hash(dataNascimento, enderecoPrincipal, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(dataNascimento, other.dataNascimento)
				&& Objects.equals(enderecoPrincipal, other.enderecoPrincipal) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome);
	}
	
}
