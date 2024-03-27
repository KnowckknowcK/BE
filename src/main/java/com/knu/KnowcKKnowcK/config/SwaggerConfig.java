package com.knu.KnowcKKnowcK.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("똑똑('KnowcKKnowcK') API Docs")
                .description("종합설계프로젝트 6팀 똑똑('KnowcKKnowcK')의 API 명세입니다.")
                .version("1.0.0");
    }
}
