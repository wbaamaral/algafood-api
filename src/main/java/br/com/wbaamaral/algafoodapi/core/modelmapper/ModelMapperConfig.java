package br.com.wbaamaral.algafoodapi.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		// personalizar o mapeamento é dessa forma
		// modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
		// .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

		return modelMapper;
	}

}