package br.com.wbaamaral.algafoodapi.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.MappingJacksonValue;

import br.com.wbaamaral.algafoodapi.api.model.PedidoModel;
import br.com.wbaamaral.algafoodapi.api.model.PedidoResumoModel;
import br.com.wbaamaral.algafoodapi.api.model.input.PedidoInput;
import br.com.wbaamaral.algafoodapi.domain.filter.PedidoFilter;

public interface PedidoControllerOpenApi {

	public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

	public MappingJacksonValue listar(String campos);

	public PedidoModel buscar(String codigoPedido);

	public PedidoModel adicionar(PedidoInput pedidoInput);
}
