package com.chuuzr.chuuzrbackend.config;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
    TAG_ORDER.put("Room Users", 5);
    TAG_ORDER.put("Room Options", 6);
  }

  @Bean
  public OpenAPI chuuzrOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("Chuuzr Backend API")
            .version("v1")
            .description("REST API for Chuuzr's backend services."))
        .components(new Components()
            .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")))
        .tags(List.of(
            new Tag().name("Authentication").description("OTP-driven authentication and JWT issuance."),
            new Tag().name("Users").description("User profile management endpoints."),
            new Tag().name("Rooms").description("Create, update, and retrieve rooms."),
            new Tag().name("Option Types").description("Define and manage option categories."),
            new Tag().name("Options").description("CRUD operations for standalone options."),
            new Tag().name("Room Users").description("Manage users invited to rooms."),
            new Tag().name("Room Options").description("Manage options linked to a specific room.")));
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
