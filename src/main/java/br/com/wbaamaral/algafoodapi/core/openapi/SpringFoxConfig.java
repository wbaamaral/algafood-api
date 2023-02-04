package br.com.wbaamaral.algafoodapi.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.wbaamaral.algafoodapi.api.exceptionhandler.Problem;
import br.com.wbaamaral.algafoodapi.api.model.CozinhaModel;
import br.com.wbaamaral.algafoodapi.api.model.PedidoResumoModel;
import br.com.wbaamaral.algafoodapi.api.openapi.model.CozinhasModelOpenApi;
import br.com.wbaamaral.algafoodapi.api.openapi.model.PageableModelOpenApi;
import br.com.wbaamaral.algafoodapi.api.openapi.model.PedidosResumoModelOpenApi;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

   @Bean
   Docket apiDocket() {
      var typeResolver = new TypeResolver();
      
//    Incluir parâmetro implicito global
//    .globalRequestParameters(Collections.singletonList(
//            new RequestParameterBuilder()
//                    .name("campos")
//                    .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//                    .in(ParameterType.QUERY)
//                    .required(false)
//                    .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                    .build())
//    )
      
      return new Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.wbaamaral.algafoodapi"))
            .paths(PathSelectors.any())
            .build()
         .useDefaultResponseMessages(false)
         .globalResponses(HttpMethod.GET, globalGetResponseMessages())
         .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
         .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
         .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
         .additionalModels(typeResolver.resolve(Problem.class))
         .ignoredParameterTypes(ServletWebRequest.class)
         .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
         .alternateTypeRules(AlternateTypeRules.newRule(
               typeResolver.resolve(Page.class, CozinhaModel.class),
               CozinhasModelOpenApi.class))
         .alternateTypeRules(AlternateTypeRules.newRule(
                 typeResolver.resolve(Page.class, PedidoResumoModel.class),
                 PedidosResumoModelOpenApi.class))
         .apiInfo(apiInfo())
         .tags(new Tag("Cidades", "Gerencia as cidades"),
               new Tag("Grupos", "Gerencia os grupos de usuários"),
               new Tag("Cozinhas", "Gerencia as cozinhas"),
               new Tag("Pedidos", "Gerencia os pedidos"),
               new Tag("Restaurantes", "Gerencia os restaurantes"),
               new Tag("Estados", "Gerencia os Estados"),
               new Tag("Formas de pagamento", "Gerencia as formas de pagamento"));
   }

   @Bean
   public JacksonModuleRegistrar springFoxJacksonConfig() {
      return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
   }

   private List<Response> globalGetResponseMessages() {
      return Arrays.asList(
            new ResponseBuilder()
                  .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                  .description("Erro interno do Servidor")
                  .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
                  .build(),
            new ResponseBuilder()
                  .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                  .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                  .build()
      );
   }

   private List<Response> globalPostPutResponseMessages() {
      return Arrays.asList(
            new ResponseBuilder()
                  .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                  .description("Requisição inválida (erro do cliente)")
                  .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
                  .build(),
            new ResponseBuilder()
                  .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                  .description("Erro interno no servidor")
                  .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
                  .build(),
            new ResponseBuilder()
                  .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                  .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                  .build(),
            new ResponseBuilder()
                  .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                  .description("Requisição recusada porque o corpo está em um formato não suportado")
                  .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
                  .build()
      );
   }

   private List<Response> globalDeleteResponseMessages() {
      return Arrays.asList(
            new ResponseBuilder()
                  .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                  .description("Requisição inválida (erro do cliente)")
                  .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
                  .build(),
            new ResponseBuilder()
                  .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                  .description("Erro interno no servidor")
                  .representation( MediaType.APPLICATION_JSON )
                  .apply(getProblemaModelReference())
                  .build()
      );
   }

   private Consumer<RepresentationBuilder> getProblemaModelReference() {
      return r -> r.model(m -> m.name("Problema")
            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                  q -> q.name("Problema").namespace("br.com.wbaamaral.algafoodapi.api.exceptionhandler")))));
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