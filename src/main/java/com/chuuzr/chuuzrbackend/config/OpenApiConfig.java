package com.chuuzr.chuuzrbackend.config;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {
  public static final String SECURITY_SCHEME_NAME = "BearerAuth";

  private static final Map<String, Integer> TAG_ORDER;

  static {
    TAG_ORDER = new LinkedHashMap<>();
    TAG_ORDER.put("Authentication", 0);
    TAG_ORDER.put("Users", 1);
    TAG_ORDER.put("Rooms", 2);
    TAG_ORDER.put("Option Types", 3);
    TAG_ORDER.put("Options", 4);
    TAG_ORDER.put("Search", 5);
  }

  @Bean
  public OpenAPI chuuzrOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("Chuuzr Backend API")
            .version("v1.0.0")
            .description(
                "REST API for chuuzr's backend services. A versatile decision-making and group voting application.")
            .contact(new Contact()
                .name("Chuuzr Development Team")
                .email("dev@chuuzr.com"))
            .license(new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT")))
        .components(new Components()
            .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT token obtained from OTP verification")))
        .tags(List.of(
            new Tag().name("Authentication").description("OTP-driven authentication and JWT issuance."),
            new Tag().name("Users").description("User profile management endpoints."),
            new Tag().name("Rooms").description("Create, update, and retrieve rooms."),
            new Tag().name("Option Types").description("Define and manage option categories."),
            new Tag().name("Options").description("CRUD operations for standalone options."),
            new Tag().name("Search")
                .description("Search external providers for options based on the room's option type.")));
  }

  @Bean
  public OperationCustomizer operationCustomizer() {
    return (operation, handlerMethod) -> {
      return operation;
    };
  }

  @Bean
  public OpenApiCustomizer globalResponseCustomizer() {
    return openApi -> {
      openApi.getComponents().addSchemas("ErrorDTO",
          new io.swagger.v3.oas.models.media.Schema<>()
              .$ref("#/components/schemas/ErrorDTO"));
    };
  }

  @Bean
  public OpenApiCustomizer tagOrderingCustomizer() {
    return openApi -> {
      if (openApi.getTags() != null) {
        openApi.getTags().sort(Comparator.comparingInt(tag -> tagOrderIndex(tag.getName())));
      }
      if (openApi.getPaths() != null) {
        openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
          if (operation.getTags() != null) {
            List<String> sortedTags = operation.getTags().stream()
                .sorted(Comparator.comparingInt(this::tagOrderIndex))
                .collect(Collectors.toList());
            operation.setTags(sortedTags);
          }
        }));
      }
    };
  }

  private int tagOrderIndex(String tagName) {
    return TAG_ORDER.getOrDefault(tagName, Integer.MAX_VALUE);
  }
}
