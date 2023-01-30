package br.com.wbaamaral.algafoodapi.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("br.com.wbaamaral.algafoodapi"))
                .paths(PathSelectors.any())
//              .paths(PathSelectors.ant("/restaurantes/*"))                
                .build();
    }

}