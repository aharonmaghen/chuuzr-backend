package com.chuuzr.chuuzrbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.chuuzr.chuuzrbackend.model.Room;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoomTest {
  @Autowired
  TestRestTemplate restTemplate;
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Test
  void shouldReturnARoom() {
    ResponseEntity<String> response = restTemplate.getForEntity("/api/rooms/dfc98e14-8bb9-4f76-af51-70afaaaaae4e", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    String uuid = documentContext.read("$.uuid");
    String name = documentContext.read("$.name");
    String updatedAt = documentContext.read("$.updatedAt");
    String createdAt = documentContext.read("$.createdAt");
    assertThat(uuid).isEqualTo("dfc98e14-8bb9-4f76-af51-70afaaaaae4e");
    assertThat(name).isEqualTo("Aharon Test Room");
    assertThat(updatedAt).isNotNull();
    assertThat(createdAt).isNotNull();
  }

  @Test
  void shouldCreateARoom() {
    Room request = new Room(null, null, "Test Room 2", null, null);
    ResponseEntity<Void> response = restTemplate.postForEntity("/api/rooms", request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    URI locationOfNewRoom = response.getHeaders().getLocation();
    ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewRoom, String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    String uuid = documentContext.read("$.uuid");
    String name = documentContext.read("$.name");
    String updatedAt = documentContext.read("$.updatedAt");
    String createdAt = documentContext.read("$.createdAt");
    assertThat(uuid).isNotNull();
    assertThat(name).isEqualTo("Test Room 2");
    assertThat(updatedAt).isNotNull();
    assertThat(createdAt).isNotNull();
  }

  @Test
  void shouldUpdateARoom() {
    Room room = new Room(null, null, "Aharon Test Room Edited", null, null);
    HttpEntity<Room> request = new HttpEntity<>(room);

    ResponseEntity<Void> response = restTemplate.exchange("/api/rooms/dfc98e14-8bb9-4f76-af51-70afaaaaae4e", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/rooms/dfc98e14-8bb9-4f76-af51-70afaaaaae4e", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    String uuid = documentContext.read("$.uuid");
    String name = documentContext.read("$.name");
    String updatedAt = documentContext.read("$.updatedAt");
    String createdAt = documentContext.read("$.createdAt");
    assertThat(uuid).isEqualTo("dfc98e14-8bb9-4f76-af51-70afaaaaae4e");
    assertThat(name).isEqualTo("Aharon Test Room Edited");
    assertThat(updatedAt).isNotNull();
    assertThat(createdAt).isNotNull();
  }

  @BeforeEach
  void setUp() {
    jdbcTemplate.update("UPDATE rooms SET name='Aharon Test Room' WHERE id=8;");
  }
}