package com.bnr.banking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "BNR Banking System API", version = "1.0.0", description = "National Bank of Rwanda Banking System Backend API"))
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BNR Banking System API")
                        .version("1.0.0")
                        .description("The backend API for the National Bank of Rwanda (NBR). It supports customer management, banking operations, and transaction handling.")
                        .contact(new Contact()
                                .name("BNR IT Department")
                                .email("it@bnr.rw")
                                .url("https://www.bnr.rw")))
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8080").description("Local server"),
                        new Server().url("https://api.bnr.rw").description("Production server")
                ));
    }
}
