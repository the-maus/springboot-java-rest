package br.com.maus.integrationtests.controllers.yaml;

import br.com.maus.config.TestConfigs;
import br.com.maus.integrationtests.controllers.yaml.mapper.YAMLMapper;
import br.com.maus.integrationtests.dto.AccountCredentialsDTO;
import br.com.maus.integrationtests.dto.TokenDTO;
import br.com.maus.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerYamlTest extends AbstractIntegrationTest {

    private static YAMLMapper objectMapper;

    private static TokenDTO token;

    @BeforeAll
    static void setUp() {
        objectMapper = new YAMLMapper();
        token = new TokenDTO();
    }

    @Test
    @Order(1)
    void signIn() throws JsonProcessingException {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("leandro", "admin123");

        token = given()
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .basePath("/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                    .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(credentials, objectMapper)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(TokenDTO.class, objectMapper);

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }

    @Test
    @Order(1)
    void refreshToken() throws JsonProcessingException {
        token = given()
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .basePath("/auth/refresh/")
                .port(TestConfigs.SERVER_PORT)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .pathParam("username", token.getUsername())
                    .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getRefreshToken())
                .when()
                    .put("{username}")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(TokenDTO.class, objectMapper);

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }
}