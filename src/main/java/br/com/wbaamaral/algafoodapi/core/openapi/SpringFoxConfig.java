package br.com.wbaamaral.algafoodapi.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30).select()
//                .apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("br.com.wbaamaral.algafoodapi"))
				.paths(PathSelectors.any())
//              .paths(PathSelectors.ant("/restaurantes/*"))                
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {

		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1.0")
				.contact(getContact())
				.build();
	}

	private Contact getContact() {
		Contact contact = new Contact("wba_amaral", 
				"https://www.wbaamaral.com.br", 
				"contato@wbaamral.com.br");

		return contact;
	}
}