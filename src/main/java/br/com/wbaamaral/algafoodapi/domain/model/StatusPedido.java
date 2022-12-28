package br.com.wbaamaral.algafoodapi.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	// @formatter:off
 
	CRIADO ("Criado"), 
	CONFIRMADO ("Confirmado", CRIADO), 
	ENTREGUE ("Entregue", CONFIRMADO), 
	CANCELADO ("Cancelado", CRIADO);

	// @formatter:on

	private String descricao;
	private List<StatusPedido> statusAnteriores;

	StatusPedido(String descicao, StatusPedido... statusAnteriores) {

		this.descricao = descicao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}

	public String getDescricao() {

		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		
		return !novoStatus.statusAnteriores.contains(this);
	}
}
