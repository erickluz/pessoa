package org.erick.pessoa.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class PessoaEnderecoId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long idPessoa;
	private Long idEndereco;
	
	public PessoaEnderecoId() {
		
	}
	
	public PessoaEnderecoId(Long idPessoa, Long idEndereco) {
		this.idPessoa = idPessoa;
		this.idEndereco = idEndereco;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEndereco, idPessoa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaEnderecoId other = (PessoaEnderecoId) obj;
		return Objects.equals(idEndereco, other.idEndereco) && Objects.equals(idPessoa, other.idPessoa);
	}
	
}
