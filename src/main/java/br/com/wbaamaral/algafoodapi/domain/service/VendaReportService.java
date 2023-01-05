package br.com.wbaamaral.algafoodapi.domain.service;

import br.com.wbaamaral.algafoodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	
}