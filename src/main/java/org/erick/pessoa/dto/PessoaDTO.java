package org.erick.pessoa.dto;

import java.io.Serializable;
import java.util.Objects;

import org.erick.pessoa.domain.Endereco;

public class PessoaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String dataNascimento;
	private Endereco enderecoPrincipal;
	
	public PessoaDTO() {
	}
	public PessoaDTO(Long id, String nome, String dataNascimento, Endereco enderecoPrincipal) {
		this.id = id;
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
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Endereco getEnderecoPrincipal() {
		return enderecoPrincipal;
	}
	public void setEnderecoPrincipal(Endereco enderecoPrincipal) {
		this.enderecoPrincipal = enderecoPrincipal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
		PessoaDTO other = (PessoaDTO) obj;
		return Objects.equals(dataNascimento, other.dataNascimento)
				&& Objects.equals(enderecoPrincipal, other.enderecoPrincipal) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome);
	}

}
