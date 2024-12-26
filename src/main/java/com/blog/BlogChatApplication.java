package com.blog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Blog Chat API",
        version = "1.0",
        description = "Blog Chat REST API Documentation"
    )
)
public class BlogChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogChatApplication.class, args);
    }
}
