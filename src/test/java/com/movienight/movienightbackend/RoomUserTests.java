package com.movienight.movienightbackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoomUserTests {
  @Autowired
  TestRestTemplate restTemplate;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void shouldReturnAPageOfUsersOfARoom() {
    ResponseEntity<String> response = restTemplate.getForEntity("/api/room-users/dfc98e14-8bb9-4f76-af51-70afaaaaae4e/users?page=0&size=1", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    JSONArray page = documentContext.read("$[*]");
    assertThat(page.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnASortedPageOfUsersOfARoom() {
    ResponseEntity<String> response = restTemplate
        .getForEntity("/api/room-users/7db07b43-ce20-4c6a-bcad-0ef47b66e2bc/users?page=0&size=1&sort=userId,desc", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    JSONArray page = documentContext.read("$[*]");
    assertThat(page.size()).isEqualTo(1);

    String uuid = documentContext.read("$[0].uuid");
    assertThat(uuid).isEqualTo("9bef0d8f-cd75-4429-9642-d7a2a1140855");
  }

  @Test
  void shouldAddAUserToARoom() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("userUuid", "c759fe59-d24c-4bb6-bdf8-52f4d08a8181");

    ResponseEntity<Void> response = restTemplate.postForEntity("/api/room-users/dfc98e14-8bb9-4f76-af51-70afaaaaae4e/add-user", requestBody, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    URI locationOfNewRoomUser = response.getHeaders().getLocation();
    ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewRoomUser, String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @BeforeEach
  void setUp() {
    jdbcTemplate.execute("DELETE FROM room_users WHERE room_id = 8 and user_id = 20;");
  }
}
