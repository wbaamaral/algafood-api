package br.com.wbaamaral.algafoodapi.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import br.com.wbaamaral.algafoodapi.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@NotNull
	@FileSize( max = "500KB")
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
	
}