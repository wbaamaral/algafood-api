package br.com.wbaamaral.algafoodapi.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;

public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;
    
}        