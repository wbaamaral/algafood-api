package br.com.wbaamaral.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.wbaamaral.algafoodapi.domain.model.Cozinha;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroCozinhaService;

@SpringBootTest
public class CadastroCozinhaIntegrationTests {

   @Autowired
   private CadastroCozinhaService cadastroCozinha;

   /*
    * Caminho feliz
    */
   @Test
   public void testarCadastroCozinhaComSucesso() {

      // cenário
      Cozinha novaCozinha = new Cozinha();
      novaCozinha.setNome("Chinesa");

      // ação
      novaCozinha = cadastroCozinha.salvar(novaCozinha);

      // validação
      assertThat(novaCozinha).isNotNull();
      assertThat(novaCozinha.getId()).isNotNull();
   }

   /*
    * Caminho infeliz
    */
   @Test
   public void testarCadastroCozinhaSemNome() {

      Cozinha novaCozinha = new Cozinha();
      novaCozinha.setNome(null);

      ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
         cadastroCozinha.salvar(novaCozinha);
      });

      System.out.println(erroEsperado.toString());

      assertThat(erroEsperado).isNotNull();
   }

}