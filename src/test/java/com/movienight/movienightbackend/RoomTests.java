package com.movienight.movienightbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.movienight.movienightbackend.models.Room;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoomTests {
  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnARoom() {
    ResponseEntity<String> response = restTemplate.getForEntity("/api/rooms/1", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Number id = documentContext.read("$.id");
    String name = documentContext.read("$.name");
    assertThat(id).isEqualTo(1);
    assertThat(name).isEqualTo("Aharon Test Room");
  }

  @Test
  @DirtiesContext
  void shouldCreateARoom() {
    Room request = new Room(null, "Test Room 2", null, null);
    ResponseEntity<Void> response = restTemplate.postForEntity("/api/rooms", request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    URI locationOfNewRoom = response.getHeaders().getLocation();
    ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewRoom, String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    String name = documentContext.read("$.name");
    assertThat(id).isNotNull();
    assertThat(name).isEqualTo("Test Room 2");
  }

  @Test
  void shouldUpdateARoom() {
    Room room = new Room(null, "Aharon Test Room Edited", null, null);
    HttpEntity<Room> request = new HttpEntity<>(room);
    
    ResponseEntity<Void> response = restTemplate.exchange("/api/rooms/1", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/rooms/1", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    String name = documentContext.read("$.name");
    assertThat(id).isEqualTo(1);
    assertThat(name).isEqualTo("Aharon Test Room Edited");
  }
}