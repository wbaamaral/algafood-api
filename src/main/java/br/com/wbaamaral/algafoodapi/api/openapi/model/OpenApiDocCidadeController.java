package br.com.wbaamaral.algafoodapi.api.openapi.model;

import java.util.List;

import br.com.wbaamaral.algafoodapi.api.exceptionhandler.Problem;
import br.com.wbaamaral.algafoodapi.api.model.CidadeModel;
import br.com.wbaamaral.algafoodapi.api.model.input.CidadeInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface OpenApiDocCidadeController {

   @ApiOperation("Lista as cidades")
   public List<CidadeModel> listar();

   @ApiOperation("Buscar uma cidade po ID")
   @ApiResponses({
         @ApiResponse(code = 400, message = "ID da Cidade inválido", response = Problem.class),
         @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
   })
   public CidadeModel buscar(
         @ApiParam(value = "ID de uma cidade", example = "1")
         Long cidadeId);

   @ApiOperation("Cadastrar uma cidade")
   @ApiResponses({
      @ApiResponse(code = 201, message = "Cidade cadastrada")
   })
   public CidadeModel adicionar(
         @ApiParam( name = "corpo", value = "Representação de uma cidade")
         CidadeInput cidade);

   @ApiOperation("Atualiza uma cidade por ID")
   @ApiResponses({
         @ApiResponse(code = 200, message = "Cidade atualizada"),
         @ApiResponse(code = 404, message = "cidade não encontrada", response = Problem.class )
   })
   public CidadeModel atualizar(
         @ApiParam(value = "ID de uma cidade", example = "1")
         Long cidadeId,
         @ApiParam(name = "Corpo", value = "Representação de uma cidade com os novos dados")
         CidadeInput cidade);
   
   @ApiOperation("Exluir uma cidade por ID")
   @ApiResponses({
      @ApiResponse(code = 204, message = "Cidade excluida"),
      @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
      @ApiResponse(code = 409, message = "Cidade não pode ser exluída, pois esta em uso", response = Problem.class)
   })
   public void remover(
         @ApiParam(value = "ID de uma cidade", example = "1")
         Long cidadeId);

}
