package org.erick.pessoa.dto;

import java.io.Serializable;
import java.util.Objects;

public class EnderecoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idPessoa;
	private String logradouro;
	private String cep;
	private String numero;
	private String cidade;
	
	public EnderecoDTO() {
		
	}
	
	public EnderecoDTO(Long idPessoa, String logradouro, String cep, String numero, String cidade) {
		this.idPessoa = idPessoa;
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
	}
	public Long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cep, cidade, idPessoa, logradouro, numero);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoDTO other = (EnderecoDTO) obj;
		return Objects.equals(cep, other.cep) && Objects.equals(cidade, other.cidade)
				&& Objects.equals(idPessoa, other.idPessoa) && Objects.equals(logradouro, other.logradouro)
				&& Objects.equals(numero, other.numero);
	}

	
}
