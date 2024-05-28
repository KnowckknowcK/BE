package com.knu.KnowcKKnowcK.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${client.local.url}")
    private String localUrl;
    @Value("${client.top.base.url}")
    private String baseUrl;
    @Value("${client.base.url}")
    private String topBaseUrl;
    @Value("${server.base.url}")
    private String serverUrl;
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins(localUrl,baseUrl,topBaseUrl,serverUrl)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

    }
}
