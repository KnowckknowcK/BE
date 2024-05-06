package com.knu.KnowcKKnowcK.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${server.base.url}")
    String serverUrl;
    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(serverUrl);

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(List.of(server));
    }

    private Info apiInfo() {
        return new Info()
                .title("똑똑('KnowcKKnowcK') API Docs")
                .description("종합설계프로젝트 6팀 똑똑('KnowcKKnowcK')의 API 명세입니다.")
                .version("1.0.0");
    }
}
