package br.com.wbaamaral.algafoodapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wbaamaral.algafoodapi.api.model.GrupoModel;
import br.com.wbaamaral.algafoodapi.domain.model.Grupo;

@Component
public class GrupoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public GrupoModel toModel(Grupo grupo) {

		return modelMapper.map(grupo, GrupoModel.class);
	}

	public List<GrupoModel> toCollectionModel(List<Grupo> grupos) {

		// @formatter:off
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());
		// @formatter:on
	}
}
