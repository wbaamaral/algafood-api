package br.com.wbaamaral.algafoodapi.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import br.com.wbaamaral.algafoodapi.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {

	@Value("${algafood.storage.local.diretorio-fotos}")
	private Path diretorioFotos;

	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	}

	// https://dicasdejava.com.br/java-como-obter-a-extensao-de-um-arquivo/
	@Override
	public String getExtencaoArquivo(String file) {

		var extensao = "";
		if (file.contains(".")) {
			extensao = file.substring(file.lastIndexOf("."));
		}

		return extensao.toLowerCase();
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeAquivo());

			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));

		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);

			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}

	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);

			FotoRecuperada fotoRecuperada = FotoRecuperada.builder().inputStream(Files.newInputStream(arquivoPath))
					.build();

			return fotoRecuperada;

		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o arquivo.", e);
		}
	}

}