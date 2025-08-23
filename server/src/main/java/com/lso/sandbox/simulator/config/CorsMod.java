package com.lso.sandbox.simulator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Module d'activation du CORS
 * <p>
 * Permet d'utiliser l'application serveur (ici, disponible sur le port 8080) depuis un frontend local exposé sur un
 * autre port (dans notre cas sur le port 5173)
 * <p>
 * @link <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Guides/CORS">Source</a>
 */
@Configuration
public class CorsMod {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
