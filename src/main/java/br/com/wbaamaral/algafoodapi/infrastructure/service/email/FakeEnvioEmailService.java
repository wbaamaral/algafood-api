package br.com.wbaamaral.algafoodapi.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

	@Override
	public void enviar(Mensagem mensagem) {
		log.info("=====[ F A K E   E - M A I L ]=====");
		// Foi necessário alterar o modificador de acesso do método processarTemplate
		// da classe pai para "protected", para poder chamar aqui

		log.info("=====[ processando ... fakemail ]=====");
		String corpo = processarTemplate(mensagem);

		log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
	}

}