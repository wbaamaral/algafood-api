package br.com.wbaamaral.algafoodapi.domain.model;

public enum StatusPedido {

	CRIADO ("Criado"), 
	CONFIRMADO ("Confirmado"), 
	ENTREGUE ("Entregue"), 
	CANCELADO ("Cancelado");
	
	private String descricao;
	
	StatusPedido(String descicao) {
		
		this.descricao = descicao;
	}
	
	public String getDescricao() {
		
		return this.descricao;
	}
}
