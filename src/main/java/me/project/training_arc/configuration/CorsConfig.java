package me.project.training_arc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Разрешить CORS для всех эндпоинтов
                        .allowedOrigins("http://localhost:5173", "http:127.0.0.1:5173")  // Разрешенный домен фронтенда
                        .allowedMethods("*")  // Разрешенные HTTP-методы
                        .allowedHeaders("*")  // Разрешенные заголовки
                        .allowCredentials(true);  // Разрешить передачу куки и авторизационных данных
            }
        };
    }
}