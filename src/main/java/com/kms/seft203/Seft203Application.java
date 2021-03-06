package com.kms.seft203;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SuppressWarnings("unused")
@SpringBootApplication(
        exclude = {SecurityAutoConfiguration.class}
)
public class Seft203Application {
    private static final Logger logger = LoggerFactory.getLogger(Seft203Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Seft203Application.class, args);
    }

    @Bean
    CommandLineRunner runner(AppVersionRepository repo) {
        return args -> repo.save(new AppVersion(1L, "SEFT Program", "1.0.0"));
    }

    @Bean
    CommandLineRunner sayHello() {
        return args -> logger.info("Check out http://localhost:8080/swagger-ui.html");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS")
                        .allowedOrigins("*");
            }
        };
    }
}
