package br.com.wbaamaral.algafoodapi.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.wbaamaral.algafoodapi.api.exceptionhandler.Problem;
import br.com.wbaamaral.algafoodapi.api.model.PedidoModel;
import br.com.wbaamaral.algafoodapi.api.model.PedidoResumoModel;
import br.com.wbaamaral.algafoodapi.api.model.input.PedidoInput;
import br.com.wbaamaral.algafoodapi.domain.filter.PedidoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

   @ApiImplicitParams({
      @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string")
})
@ApiOperation("Pesquisa os pedidos")
public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

@ApiOperation("Registra um pedido")
@ApiResponses({
      @ApiResponse(code = 201, message = "Pedido registrado"),
})
public PedidoModel adicionar(
      @ApiParam(name = "corpo", value = "Representação de um novo pedido")
            PedidoInput pedidoInput);

@ApiImplicitParams({
      @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string")
})
@ApiOperation("Busca um pedido por código")
@ApiResponses({
      @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
})
public PedidoModel buscar(
      @ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
            String codigoPedido);

}
