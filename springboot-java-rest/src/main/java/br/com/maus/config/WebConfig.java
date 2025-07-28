package br.com.maus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //this class contain configurations and may contain beans declaration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originPatterns}") // sets the property from application.yml to corsOriginPatterns variable
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**") // applies to all requests
                .allowedOrigins(allowedOrigins)
//                .allowedMethods("GET", "POST", "DELETE", "PATCH")
                .allowedMethods("*") // all methods
                .allowCredentials(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        // Via EXTENSION (http://localhost:8080/api/person/v1/3.xml or http://localhost:8080/api/person/v1/3.json) Deprecated on Spring Boot 2.6

        // Via QUERY PARAM http://localhost:8080/api/person/v1/3?format=xml
//        configurer.favorParameter(true)
//                .parameterName("format")
//                .ignoreAcceptHeader(true)
//                .useRegisteredExtensionsOnly(true)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                    .mediaType("json", MediaType.APPLICATION_JSON)
//                    .mediaType("xml", MediaType.APPLICATION_XML);

        // Via Accept Header http://localhost:8080/api/person/v1/3
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(true)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);
    }


}
