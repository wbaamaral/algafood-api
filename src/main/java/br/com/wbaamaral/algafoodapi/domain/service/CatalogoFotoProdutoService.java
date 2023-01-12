package br.com.wbaamaral.algafoodapi.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wbaamaral.algafoodapi.domain.exception.FotoProdutoNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.model.FotoProduto;
import br.com.wbaamaral.algafoodapi.domain.repository.ProdutoRepository;
import br.com.wbaamaral.algafoodapi.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoStorageService fotoStorage;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		String novoNome;
		FotoProduto fotoSalva;
		String nomeArquivoExistente = null;

		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getIdRestaurante(),
				foto.getIdProduto());

		if (fotoExistente.isPresent()) {
			// excluir foto
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		novoNome = fotoStorage.getNovoNome(foto.getNomeArquivo());
		foto.setNomeArquivo(novoNome);

		fotoSalva = produtoRepository.save(foto);
		produtoRepository.flush();

		// @formatter:off
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeAquivo(novoNome)
				.inputStream(dadosArquivo)
				.build();
		// @formatter:on

		fotoStorage.substituir(nomeArquivoExistente, novaFoto);

		return fotoSalva;
	}

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}
}
