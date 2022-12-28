package br.com.wbaamaral.algafoodapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.RestauranteModel;
import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

   @Autowired
   private ModelMapper modelMapper;

   public RestauranteModel toModel(Restaurante restaurante) {

      return modelMapper.map(restaurante, RestauranteModel.class);
   }

   public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {

      // @formatter:off

		return restaurantes.stream()
		      .map(restaurante -> toModel(restaurante))
		      .collect(Collectors.toList());

		// @formatter:on
   }
}
