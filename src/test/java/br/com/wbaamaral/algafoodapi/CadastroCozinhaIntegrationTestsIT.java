package br.com.wbaamaral.algafoodapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import br.com.wbaamaral.algafoodapi.domain.model.Cozinha;
import br.com.wbaamaral.algafoodapi.domain.repository.CozinhaRepository;
import br.com.wbaamaral.algafoodapi.util.DatabaseCleaner;
import br.com.wbaamaral.algafoodapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIntegrationTestsIT {

  private final String BASE_URI = "/cozinhas";
  private final String[] NOMECOZINHA = { "Tailandesa", "Americana" };
  private static final int COZINHA_ID_INEXISTENTE = 100;

  private Cozinha cozinhaAmericana;
  private int quantidadeCozinhasCadastradas;
  private String jsonCorretoCozinhaChinesa;

  @LocalServerPort
  private int port;

  @Autowired
  private DatabaseCleaner dataBaseCleaner;

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @BeforeEach
  public void setuUP() {

    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.basePath = BASE_URI;
    RestAssured.port = port;

    quantidadeCozinhasCadastradas = NOMECOZINHA.length;
    jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
        "/json/correto/cozinha-chinesa.json");
    dataBaseCleaner.clearTables();
    prepararDados();

  }

  private void prepararDados() {
    Cozinha cozinhaTailandesa = new Cozinha();
    cozinhaTailandesa.setNome(NOMECOZINHA[0]);
    cozinhaRepository.save(cozinhaTailandesa);

    cozinhaAmericana = new Cozinha();
    cozinhaAmericana.setNome(NOMECOZINHA[1]);
    cozinhaRepository.save(cozinhaAmericana);

    quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
  }

  @Test
  public void devRetornarStatus200_QaundoConsultarCozinhas() {

    // @formatter:off
      given()
         .accept(ContentType.JSON)
       .when()
         .get()
       .then()
         .statusCode(HttpStatus.OK.value());

      // @formatter:on
  }

  @Test
  public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {

    given()
        .accept(ContentType.JSON)
        .when()
        .get()
        .then()
        .body("", hasSize(quantidadeCozinhasCadastradas));

  }

  @Test
  public void deveRetornarStatus201_QuandoCadastrarCozinha() {

    given()
        .body(jsonCorretoCozinhaChinesa)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .when()
        .post()
        .then()
        .statusCode(HttpStatus.CREATED.value());

  }

  @Test
  public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
    given()
        .pathParam("cozinhaId", cozinhaAmericana.getId())
        .accept(ContentType.JSON)
        .when()
        .get("/{cozinhaId}")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("nome", equalTo(cozinhaAmericana.getNome()));
  }

  @Test
  public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {

    given()
        .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
        .accept(ContentType.JSON)
        .when()
        .get("/{cozinhaId}")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());

  }

}