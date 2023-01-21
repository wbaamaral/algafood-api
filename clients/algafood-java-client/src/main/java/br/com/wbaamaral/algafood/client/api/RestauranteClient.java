package br.com.wbaamaral.algafood.client.api;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import br.com.wbaamaral.algafood.client.model.RestauranteResumoModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestauranteClient {

   private static final String RESOURCE_PATH = "/restaurantes";

   private RestTemplate restTemplate;
   private String url;

   public List<RestauranteResumoModel> listar() {

      URI resourceUri = URI.create(url + RESOURCE_PATH);

      RestauranteResumoModel[] restaurantes = restTemplate.getForObject(resourceUri, RestauranteResumoModel[].class);

      return Arrays.asList(restaurantes);
   }
}