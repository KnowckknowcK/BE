package com.knu.KnowcKKnowcK.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${server.base.url}")
    String serverUrl;
    String localUrl = "http://localhost:8080";
    @Bean
    public OpenAPI openAPI() {
        String jwt = "accessToken";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        Server prod = new Server();
        Server local = new Server();

        prod.setUrl(serverUrl);
        local.setUrl(localUrl);

        return new OpenAPI()
                .components(components)
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .servers(List.of(prod,local));
    }

    private Info apiInfo() {
        return new Info()
                .title("똑똑('KnowcKKnowcK') API Docs")
                .description("종합설계프로젝트 6팀 똑똑('KnowcKKnowcK')의 API 명세입니다.")
                .version("1.0.0");
    }
}
