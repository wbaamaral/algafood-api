package br.com.wbaamaral.algafoodapi.infrastructure.repository.service.report;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.filter.VendaDiariaFilter;
import br.com.wbaamaral.algafoodapi.domain.service.VendaReportService;

@Service
public class PdfVendaReportService implements VendaReportService {

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		return null;
	}

}