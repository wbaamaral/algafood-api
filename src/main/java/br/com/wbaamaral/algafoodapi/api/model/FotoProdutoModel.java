package br.com.wbaamaral.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModel {

   @ApiModelProperty(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0.jpg")
   private String nomeArquivo;

   @ApiModelProperty(example = "Prime Rib ao ponto")
   private String descricao;

   @ApiModelProperty(example = "image/jpeg")
   private String contentType;

   @ApiModelProperty(example = "202912")
   private Long tamanho;
}
