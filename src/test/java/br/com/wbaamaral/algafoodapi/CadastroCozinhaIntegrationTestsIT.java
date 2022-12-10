package br.com.wbaamaral.algafoodapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
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
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.http.ContentTypeSubTypeExtractor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIntegrationTestsIT {

  private final String BASE_URI = "/cozinhas";

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

    dataBaseCleaner.clearTables();
    prepararDados();
  }

  private void prepararDados() {
    Cozinha cozinha1 = new Cozinha();
    cozinha1.setNome("Tailandesa");
    cozinhaRepository.save(cozinha1);

    Cozinha cozinha2 = new Cozinha();
    cozinha2.setNome("Americana");
    cozinhaRepository.save(cozinha2);
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
  public void deveConter4Cozinhas_QaundoConsultarCozinhas() {

    given()
        .accept(ContentType.JSON)
        .when()
        .get()
        .then()
        .body("", hasSize(2));

  }

  @Test
  public void testRetornarStatus201_QuandoCadastrarCozinha() {
    given()
        .body("{ \"nome\": \"Chinesa\" }")
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
        .pathParam("cozinhaId", 2)
        .when()
        .get("/{cozinhaId}")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("nome", equalTo("Americana"));

  }

  @Test
  public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {

    given()
        .pathParam("cozinhaId", 100)
        .accept(ContentType.JSON)
        .when()
        .get("/{cozinhaId}")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());

  }

}