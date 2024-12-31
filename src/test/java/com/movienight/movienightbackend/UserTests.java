package com.movienight.movienightbackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URL;

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
import org.yaml.snakeyaml.events.DocumentEndEvent;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.movienight.movienightbackend.models.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserTests {
  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnAUser() {
    ResponseEntity<String> response = restTemplate.getForEntity("/api/users/1", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Number id = documentContext.read("$.id");
    String name = documentContext.read("$.name");
    String nickname = documentContext.read("$.nickname");
    String countryCode = documentContext.read("$.countryCode");
    String phoneNumber = documentContext.read("$.phoneNumber");
    assertThat(id).isEqualTo(1);
    assertThat(name).isEqualTo("Aharon Maghen");
    assertThat(nickname).isEqualTo("Magoo");
    assertThat(countryCode).isEqualTo("IL");
    assertThat(phoneNumber).isEqualTo("0505533685");
  }

  @Test
  @DirtiesContext
  void shouldCreateAUser() {
    User request = new User(null, "John Doe", "Doey", "US", "3471112233", null, null, null);
    ResponseEntity<Void> response = restTemplate.postForEntity("/api/users", request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    URI locationOfNewUser = response.getHeaders().getLocation();
    ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewUser, String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    String name = documentContext.read("$.name");
    String nickname = documentContext.read("$.nickname");
    String countryCode = documentContext.read("$.countryCode");
    String phoneNumber = documentContext.read("$.phoneNumber");
    String profilePicture = documentContext.read("$.profilePicture");
    assertThat(id).isNotNull();
    assertThat(name).isEqualTo("John Doe");
    assertThat(nickname).isEqualTo("Doey");
    assertThat(countryCode).isEqualTo("US");
    assertThat(phoneNumber).isEqualTo("3471112233");
    assertThat(profilePicture).isEqualTo(null);
  }

  @Test
  @DirtiesContext
  void shouldUpdateAUser() {
    User user = new User(null, "Aharon Sharabi", "Magooster", "US", "5165006000", null, null, null);
    HttpEntity<User> request = new HttpEntity<>(user);
    
    ResponseEntity<Void> response = restTemplate.exchange("/api/users/1", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/users/1", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    String name = documentContext.read("$.name");
    String nickname = documentContext.read("$.nickname");
    String countryCode = documentContext.read("$.countryCode");
    String phoneNumber = documentContext.read("$.phoneNumber");
    String profilePicture = documentContext.read("$.profilePicture");
    assertThat(id).isEqualTo(1);
    assertThat(name).isEqualTo("Aharon Sharabi");
    assertThat(nickname).isEqualTo("Magooster");
    assertThat(countryCode).isEqualTo("US");
    assertThat(phoneNumber).isEqualTo("5165006000");
    assertThat(profilePicture).isEqualTo(null);
  }
}
