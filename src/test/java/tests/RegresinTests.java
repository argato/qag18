package tests;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import models.LombokUser;
import models.LombokUserData;
import org.junit.jupiter.api.Test;
import specs.Specs;

public class RegresinTests {

  @Test
  void findUsersEmailInListTest() {
    Specs.request
        .when()
        .get("/users?page=2")
        .then()
        .spec(Specs.responseSpec)
        .log().body()
        .body("data.findAll{it.email=~/.*?@reqres.in/}.email.flatten()",
              hasItem("michael.lawson@reqres.in"));
  }

  @Test
  void findUsersFirstNameInListTest() {
    Specs.request
        .when()
        .get("/users?page=2")
        .then()
        .spec(Specs.responseSpec)
        .log().body()
        .body("data.findAll{it.email=='michael.lawson@reqres.in'}.first_name", hasItem("Michael"));
  }

  @Test
  void findUserInListCheckSizeTest() {
    Specs.request
        .when()
        .get("/users?page=2")
        .then()
        .spec(Specs.responseSpec)
        .log().body()
        .body("data.findAll{it.email=='michael.lawson@reqres.in'}", hasSize(1));
  }

  @Test
  void findUserInListTest() {

    LombokUser expectedUser = new LombokUser();
    expectedUser.setId(7);
    expectedUser.setEmail("michael.lawson@reqres.in");
    expectedUser.setFirstName("Michael");
    expectedUser.setLastName("Lawson");
    expectedUser.setAvatar("https://reqres.in/img/faces/7-image.jpg");

    LombokUserData res = new LombokUserData();
    res.setUser(expectedUser);

    Specs.request
        .when()
        .get("/users?page=2")
        .then()
        .spec(Specs.responseSpec)
        .log().body()
        .body("data.find{it.id==7}", hasEntry("first_name", "Michael"))
        .body("data.find{it.id==7}", hasEntry("last_name", "Lawson"))
        .body("data.find{it.id==7}", hasEntry("email", "michael.lawson@reqres.in"));

  }

  @Test
  void checkSingleUserTest() {
    LombokUserData data = Specs.request
        .when()
        .get("/users/2")
        .then()
        .spec(Specs.responseSpec)
        .log().body()
        .extract().as(LombokUserData.class);

    assertEquals(2, data.getUser().getId());
    assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
    assertEquals("Janet", data.getUser().getFirstName());
    assertEquals("Weaver", data.getUser().getLastName());
    assertEquals("https://reqres.in/img/faces/2-image.jpg", data.getUser().getAvatar());

  }

  @Test
  void createUserTest() {
    Map<String, Object> data = new HashMap<>();
    data.put("name", "morpheus");
    data.put("job", "leader");
    Specs.request
        .body(data)
        .when()
        .post("/users")
        .then()
        .spec(Specs.responseCreatedSpec)
        .log().body()
        .body("name", is("morpheus"))
        .body("job", is("leader"))
        .body("id", is(notNullValue()))
        .body("createdAt", is(notNullValue()))
        .body("updatedAt", is(nullValue()));
  }

  @Test
  void putUserDataTest() {
    Map<String, Object> data = new HashMap<>();
    data.put("name", "morpheus");
    data.put("job", "zion resident");

    Specs.request
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .put("/users/2")
        .then()
        .spec(Specs.responseSpec)
        .body("name", is("morpheus"))
        .body("job", is("zion resident"))
        .body("updatedAt", is(notNullValue()))
        .body("id", is(nullValue()))
        .body("createdAt", is(nullValue()));
  }

  @Test
  void deleteUserTest() {
    Specs.request
        .when()
        .delete("/users/2")
        .then()
        .spec(Specs.responseDeletedSpec);
  }
}
