package br.com.wbaamaral.algafoodapi.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CozinhaInput {

  @NotNull
  private Long id;
}
