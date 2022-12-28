package br.com.wbaamaral.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

@JsonFilter("pedidoFilter")
@Setter
@Getter
public class PedidoResumoModelJsonFilter extends PedidoResumoModel {

}