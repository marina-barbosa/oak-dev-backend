package br.barbosa.oakdev.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
      title = "CRUD de produtos", 
      description = "Api de CRUD de produtos v1.0.0 com testes.", 
      version = "v1.0.0", 
      contact = @Contact(
                          name = "Marina Barbosa", 
                          email = "marinabarbosa.exp@gmail.com", 
                          url = "https://github.com/marina-barbosa"
                        ), 
      license = @License(
        name = "uso livre", 
        url = "https://github.com/marina-barbosa"
      )
    ), 
    servers = {
      @Server(
        url = "http://localhost:8080", 
        description = "SERVIDOR DE DESENVOLVIMENTO"
      ),
      @Server(
        url = "https://oak-dev-backend.onrender.com", 
        description = "SERVIDOR DE PRODUÇÃO"
      )
    }
)

public class SwaggerConfig {
}