package br.com.wbaamaral.algafoodapi.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.wbaamaral.algafoodapi.api.assembler.FotoProdutoModelAssembler;
import br.com.wbaamaral.algafoodapi.api.model.FotoProdutoModel;
import br.com.wbaamaral.algafoodapi.api.model.input.FotoProdutoInput;
import br.com.wbaamaral.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.model.FotoProduto;
import br.com.wbaamaral.algafoodapi.domain.model.Produto;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroProdutoService;
import br.com.wbaamaral.algafoodapi.domain.service.CatalogoFotoProdutoService;
import br.com.wbaamaral.algafoodapi.domain.service.FotoStorageService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;

	@Autowired
	private FotoStorageService fotoStorage;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

		return fotoProdutoModelAssembler.toModel(fotoProduto);
	}

	@GetMapping
	public ResponseEntity<InputStreamResource> servir(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

			InputStream inputStream = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

			// @formatter:off
			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(inputStream));
			
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.notFound().build();
		}
		// @formatter:on
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}

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