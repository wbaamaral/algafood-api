package br.com.wbaamaral.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModel {

	@ApiModelProperty(example = " \"d9f48178-53bd-4455-9241-8861a57fa7c5.jpg\"")
	private String nomeArquivo;

	@ApiModelProperty(example = "Prime Rib ao ponto")
	private String descricao;
	
	@ApiModelProperty(example = "image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example = "432550")
	private Long tamanho;
}
