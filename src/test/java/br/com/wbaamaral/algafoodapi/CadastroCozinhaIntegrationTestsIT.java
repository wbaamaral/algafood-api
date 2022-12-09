package br.com.wbaamaral.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.wbaamaral.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.wbaamaral.algafoodapi.domain.model.Cozinha;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroCozinhaService;

@SpringBootTest
public class CadastroCozinhaIntegrationTestsIT {

	private static final Long COZINHA_EXISTENTE_EM_USO = 1L;
	private static final Long COZINHA_INESISTENTE = 1_000_000_000L;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	/*
	 * Caminho feliz
	 */
	@Test
	public void deveCadastrarCozinhaComSucesso() {

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
	public void deveFalharAoCadastrarCozinhaSemNome() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinha.salvar(novaCozinha);
		});

		System.out.println(erroEsperado.toString());

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalharAoExcluirCozinhaEmUso() {

		EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinha.excluir(COZINHA_EXISTENTE_EM_USO);
		});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalharAoExcluirCozinhaInesistente() {
		
		CozinhaNaoEncontradaException erroesperado = Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinha.excluir(COZINHA_INESISTENTE);
		});
		
		assertThat(erroesperado).isNotNull();
	}
}