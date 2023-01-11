package br.com.wbaamaral.algafoodapi.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.wbaamaral.algafoodapi.api.assembler.FotoProdutoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.FotoProdutoModel;
import br.com.wbaamaral.algafoodapi.api.model.input.FotoProdutoInput;
import br.com.wbaamaral.algafoodapi.domain.model.FotoProduto;
import br.com.wbaamaral.algafoodapi.domain.model.Produto;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroProdutoService;
import br.com.wbaamaral.algafoodapi.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@Valid @PathVariable Long restauranteId, @Valid @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {

		var inputStream = fotoProdutoInput.getArquivo().getInputStream();

		var fotoSalva = catalogoFotoProduto.salvar(atribuirFoto(fotoProdutoInput, restauranteId, produtoId),
				inputStream);

		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}

	private FotoProduto atribuirFoto(FotoProdutoInput fotoProdutoInput, Long restauranteId, Long produtoId) {

		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());

		return foto;
	}

}