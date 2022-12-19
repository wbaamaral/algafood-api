package br.com.wbaamaral.algafoodapi.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidadtionConfig {

  @Bean
  public LocalValidatorFactoryBean validator(MessageSource messageSource) {

    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource);

    return bean;
  }
}
