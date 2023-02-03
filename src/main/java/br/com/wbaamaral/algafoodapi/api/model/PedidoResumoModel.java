package br.com.wbaamaral.algafoodapi.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoResumoModel {

	@ApiModelProperty(example = "c4820fe9-fc2b-40ec-8dc8-e6849bf12802")
	private String codigo;

	@ApiModelProperty(example = "298.90")
	private BigDecimal subtotal;

	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;

	@ApiModelProperty(example = "308.90")
	private BigDecimal valorTotal;

	@ApiModelProperty(example = "CRIADO")
	private String status;

	@ApiModelProperty(example = "2019-12-01T20:34:04Z")
	private OffsetDateTime dataCriacao;

	private RestauranteResumoModel restaurante;
	private UsuarioModel cliente;
}