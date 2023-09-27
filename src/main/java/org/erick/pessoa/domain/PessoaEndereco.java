package org.erick.pessoa.domain;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class PessoaEndereco {

	@EmbeddedId
	private PessoaEnderecoId pessoaEnderecoId = new PessoaEnderecoId();
	
	@ManyToOne
	@MapsId("idPessoa")
	private Pessoa pessoa;
	@ManyToOne
	@MapsId("idEndereco")
	private Endereco endereco;
	
	public PessoaEndereco() {
		
	}
	
	public PessoaEndereco( Pessoa pessoa, Endereco endereco) {
		this.pessoa = pessoa;
		this.endereco = endereco;
	}
	public PessoaEnderecoId getPessoaEnderecoId() {
		return pessoaEnderecoId;
	}
	public void setPessoaEnderecoId(PessoaEnderecoId pessoaEnderecoId) {
		this.pessoaEnderecoId = pessoaEnderecoId;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(endereco, pessoa, pessoaEnderecoId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaEndereco other = (PessoaEndereco) obj;
		return Objects.equals(endereco, other.endereco) && Objects.equals(pessoa, other.pessoa)
				&& Objects.equals(pessoaEnderecoId, other.pessoaEnderecoId);
	}

}
