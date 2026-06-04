package com.my.total_jpa_back.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        //  어떤 url을 허락하고,
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        //  어떤 HTTP 요청을 허락하고
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        //  어느 경로를 허락할 지 지정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**",
                config
        );
        return new CorsFilter(source);
    }
}
