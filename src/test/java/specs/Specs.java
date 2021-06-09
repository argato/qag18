package specs;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.is;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specs {

  public static RequestSpecification request = with()
      .baseUri("https://reqres.in")
      .basePath("/api")
      .log().all()
      .contentType(ContentType.JSON);

  public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
      .expectStatusCode(200)
      .build();

  public static ResponseSpecification responseCreatedSpec = new ResponseSpecBuilder()
      .expectStatusCode(201)
      .build();

  public static ResponseSpecification responseDeletedSpec = new ResponseSpecBuilder()
      .expectStatusCode(204)
      .expectBody(is(""))
      .build();
}
