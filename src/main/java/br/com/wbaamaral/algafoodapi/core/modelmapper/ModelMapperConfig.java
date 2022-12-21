package br.com.wbaamaral.algafoodapi.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.wbaamaral.algafoodapi.api.model.EnderecoModel;
import br.com.wbaamaral.algafoodapi.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {

        var modelMapper = new ModelMapper();

        // personalizar o mapeamento é dessa forma
        // modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
        // .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

        return modelMapper;
    }

}