package br.com.wbaamaral.algafoodapi.domain.service;

import java.util.List;

import br.com.wbaamaral.algafoodapi.domain.filter.VendaDiariaFilter;
import br.com.wbaamaral.algafoodapi.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
	
}