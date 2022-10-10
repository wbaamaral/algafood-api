package br.com.wbaamaral.alfafoodapi.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}
	
}
