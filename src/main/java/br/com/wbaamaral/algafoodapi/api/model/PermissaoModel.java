package br.com.wbaamaral.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissaoModel {

	private Long id;
	private String nome;
	private String descricao;
}