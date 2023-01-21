package br.com.wbaamaral;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import br.com.wbaamaral.algafood.client.api.RestauranteClient;

@SpringBootApplication
public class AlgafoodJavaClientApplication {

   public static void main(String[] args) {

      RestTemplate restTemplate = new RestTemplate();

      RestauranteClient restauranteClient = new RestauranteClient(
            restTemplate, "http://api.algafood.local:8080");

      restauranteClient.listar().stream().forEach(restaurante -> System.out.println(restaurante));
   }

}
