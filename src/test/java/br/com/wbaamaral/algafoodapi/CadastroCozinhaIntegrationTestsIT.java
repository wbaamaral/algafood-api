package br.com.wbaamaral.algafoodapi;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIntegrationTestsIT {

   @LocalServerPort
   private int port;

   private final String BASE_URI = "/cozinhas";

   @Test
   public void devRetornarStatus200_QaundoConsultarCozinhas() {

      // @formatter:off
      RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

      given()
         .basePath(BASE_URI)
         .port(port)
         .accept(ContentType.JSON)
       .when()
         .get()
       .then()
         .statusCode(HttpStatus.OK.value());

      // @formatter:on
   }
}