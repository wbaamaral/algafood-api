package br.com.wbaamaral.algafoodapi.core.squiggly;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

   @Override
   public void customize(TomcatServletWebServerFactory factory) {

      // código depreciado.
      //factory.addConnectorCustomizers(connector -> connector.setAttribute("relaxedQueryChars", "[]"));
      factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]"));
   }

}