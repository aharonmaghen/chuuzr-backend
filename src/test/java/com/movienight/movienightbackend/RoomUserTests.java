package com.movienight.movienightbackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoomUserTests {
  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnAPageOfUsersOfARoom() {
    ResponseEntity<String> response = restTemplate.getForEntity("/api/room-users/2/users?page=0&size=1", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    JSONArray page = documentContext.read("$[*]");
    assertThat(page.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnASortedPageOfUsersOfARoom() {
    ResponseEntity<String> response = restTemplate
        .getForEntity("/api/room-users/2/users?page=0&size=1&sort=userId,desc", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    JSONArray page = documentContext.read("$[*]");
    assertThat(page.size()).isEqualTo(1);

    Number id = documentContext.read("$[0].id");
    assertThat(id).isEqualTo(3);
  }

  @Test
  @DirtiesContext
  void shouldAddAUserToARoom() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("userId", 4);

    ResponseEntity<Void> response = restTemplate.postForEntity("/api/room-users/1/add-user", requestBody, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    URI locationOfNewRoomUser = response.getHeaders().getLocation();
    ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewRoomUser, String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
