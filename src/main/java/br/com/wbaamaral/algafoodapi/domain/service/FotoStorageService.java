package br.com.wbaamaral.algafoodapi.domain.service;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

   void armazenar(NovaFoto novaFoto);

   String getExtencaoArquivo(String file);

   @Builder
   @Getter
   class NovaFoto {

      private String nomeAquivo;
      private InputStream inputStream;

   }
}
