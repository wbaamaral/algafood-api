package br.com.wbaamaral.algafoodapi.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	void armazenar(NovaFoto novaFoto);

	void remover(String nomeArquivo);

	FotoRecuperada recuperar(String nomeArquivo);

	default String getNovoNome(String nomeArquivo) {
		var novoNome = UUID.randomUUID().toString();
		novoNome += getExtencaoArquivo(nomeArquivo);

		return novoNome.toLowerCase();
	}

	// https://dicasdejava.com.br/java-como-obter-a-extensao-de-um-arquivo/
	default String getExtencaoArquivo(String file) {

		var extensao = "";
		if (file.contains(".")) {
			extensao = file.substring(file.lastIndexOf("."));
		}

		return extensao.toLowerCase();
	}

	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);

		if (nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}

	@Builder
	@Getter
	class NovaFoto {

		private String nomeAquivo;
		private InputStream inputStream;
		private String contentType;

	}

	@Builder
	@Getter
	class FotoRecuperada {

		private InputStream inputStream;
		private String url;

		public boolean temUrl() {
			return url != null;
		}

		public boolean temInputStream() {
			return inputStream != null;
		}

	}

}
