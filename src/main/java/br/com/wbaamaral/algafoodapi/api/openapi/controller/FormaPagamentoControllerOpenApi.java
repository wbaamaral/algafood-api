package br.com.wbaamaral.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import br.com.wbaamaral.algafoodapi.api.model.FormaPagamentoModel;
import br.com.wbaamaral.algafoodapi.api.model.input.FormaPagamentoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

   @ApiOperation("Lista as formas de pagamento")
   public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);

   @ApiOperation("Busca uma forma de pagamento por ID")
   @ApiResponses(
      { @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
               @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class) }
   )
   public ResponseEntity<FormaPagamentoModel> buscar(
         @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
         ServletWebRequest request);

   @ApiOperation("Cadastra uma forma de pagamento")
   @ApiResponses({ @ApiResponse(code = 201, message = "Forma de pagamento cadastrada") })
   public FormaPagamentoModel adicionar(
         @ApiParam(
               name = "corpo", value = "Representação de uma nova forma de pagamento", required = true
         ) FormaPagamentoInput formaPagamentoInput);

   @ApiOperation("Atualiza uma forma de pagamento por ID")
   @ApiResponses(
      { @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
               @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class) }
   )
   public FormaPagamentoModel atualizar(
         @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
         @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", required = true) 
         FormaPagamentoInput formaPagamentoInput);

   @ApiOperation("Remover uma uma forma de pagamento")
   @ApiResponses(
      { @ApiResponse(code = 204, message = "Forma de pagamento removida"),
               @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
               @ApiResponse(code = 409, message = "Não é possível exluir uma forma de pagamento em uso", response = Problem.class)
      })
   public void remover(
         @ApiParam(value = "ID de uma forma de pagamento", required = true) Long formaPagamentoId);

}
