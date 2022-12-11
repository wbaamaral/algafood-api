package br.com.wbaamaral.algafoodapi.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.wbaamaral.algafoodapi.api.model.mixin.CidadeMixin;
import br.com.wbaamaral.algafoodapi.api.model.mixin.CozinhaMixin;
import br.com.wbaamaral.algafoodapi.domain.model.Cidade;
import br.com.wbaamaral.algafoodapi.domain.model.Cozinha;

@Component
public class JacksonMixinModule extends SimpleModule {

  private static final long serialVersionUID = 1L;

  public JacksonMixinModule() {

    setMixInAnnotation(Cidade.class, CidadeMixin.class);
    setMixInAnnotation(Cozinha.class, CozinhaMixin.class);

  }

}
