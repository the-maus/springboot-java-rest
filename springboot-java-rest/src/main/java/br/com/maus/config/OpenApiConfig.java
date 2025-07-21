package br.com.maus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Java Spring Boot REST API")
                .version("v1")
                .description("RESTful API from 0 with Java, Spring Boot, Kubernetes and Docker")
                .termsOfService("https://spring.io/projects/spring-boot")
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")
                )
            );
    }
}
