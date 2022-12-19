package br.com.wbaamaral.algafoodapi.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

  private int numeroMultiplo;

  @Override
  public void initialize(Multiplo constraintAnnotation) {

    this.numeroMultiplo = constraintAnnotation.numero();
  }

  @Override
  public boolean isValid(Number value, ConstraintValidatorContext context) {
    boolean valido = true;

    if (value != null) {
      var ValorDecimal = BigDecimal.valueOf(value.doubleValue());
      var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);

      // valor resto da divisao valor decimal por multiplo decimal
      var resto = ValorDecimal.remainder(multiploDecimal);

      valido = BigDecimal.ZERO.compareTo(resto) == 0;
    }

    return valido;
  }

}
