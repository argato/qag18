package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import models.UserData;
import models.LombokUserData;
import org.junit.jupiter.api.Test;
import specs.Specs;


public class UserTestWithModel {

  @Test
  void singleUserWithModel() {
    UserData data = Specs.request
        .when()
        .get("/users/2")
        .then()
        .spec(Specs.responseSpec)
        .log().body()
        .extract().as(UserData.class);

    assertEquals(2, data.getData().getId());
  }

  @Test
  void singleUserWithLombokModel() {
    LombokUserData data = Specs.request
        .when()
        .get("/users/2")
        .then()
        .spec(Specs.responseSpec)
        .log().body()
        .extract().as(LombokUserData.class);

    assertEquals(2, data.getUser().getId());
  }

}
