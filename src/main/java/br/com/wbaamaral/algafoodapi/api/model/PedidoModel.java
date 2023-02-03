package br.com.wbaamaral.algafoodapi.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoModel {

	@ApiModelProperty(example = "298.90")
	private String codigo;

	@ApiModelProperty(example = "10.00")
	private BigDecimal subtotal;

	@ApiModelProperty(example = "308.90")
	private BigDecimal taxaFrete;

	@ApiModelProperty(example = "CRIADO")
	private BigDecimal valorTotal;

	@ApiModelProperty(example = "2019-12-01T20:34:04Z")
	private String status;

	@ApiModelProperty(example = "2019-12-01T20:35:10Z")
	private OffsetDateTime dataCriacao;

	@ApiModelProperty(example = "2019-12-01T20:55:30Z")
	private OffsetDateTime dataConfirmacao;

	@ApiModelProperty(example = "2019-12-01T20:55:30Z")
	private OffsetDateTime dataEntrega;

	@ApiModelProperty(example = "2019-12-01T20:55:30Z")
	private OffsetDateTime dataCancelamento;

	private RestauranteResumoModel restaurante;
	private UsuarioModel cliente;
	private FormaPagamentoModel formaPagamento;
	private EnderecoModel enderecoEntrega;
	private List<ItemPedidoModel> itens;
}