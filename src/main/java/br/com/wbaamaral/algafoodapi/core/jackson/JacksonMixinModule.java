package br.com.wbaamaral.algafoodapi.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.wbaamaral.algafoodapi.api.model.mixin.RestauranteMixin;
import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;

public class JacksonMixinModule extends SimpleModule {

  private static final long serialVersionUID = 1L;

  public JacksonMixinModule() {

    setMixInAnnotation(Restaurante.class, RestauranteMixin.class);

  }

}
