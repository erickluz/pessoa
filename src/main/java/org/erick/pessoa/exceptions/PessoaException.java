package org.erick.pessoa.exceptions;

import java.io.Serializable;

public class PessoaException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public PessoaException(String mensagem) {
		super(mensagem);
	}
	
	public PessoaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
