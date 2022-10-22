package br.com.wbaamaral.algafoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-econtrada", "Entidade não encontrada");

	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
}
