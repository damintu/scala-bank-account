package controller

import io.restassured.RestAssured._
import io.restassured.http.ContentType
import io.restassured.matcher.RestAssuredMatchers._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.junit.Test

class AccountControllerTest {
  @Test
  def `accountCreationSuccessTest`() {
    given()
      .body("{" +
      " \"firstName\" : \"damien\"," +
      "\"lastName\" : \"sarrol\"}")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      //@TODO path should be '/' but it's currently not working for me
      .post("http://localhost:9000/account")
      .Then()
      .statusCode(201)
      .body("$",hasKey("id"))
      .body("firstName",equalTo("damien"))
      .body("lastName",equalTo("sarrol"))
  }

@Test
  def `accountCreationFailedTest`() {
    given()
      .body("{" +
        " \"firstName\" : \"damien\"")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      //@TODO path should be '/' but it's currently not working for me
      .post("http://localhost:9000/account")
      .Then()
      .statusCode(400)
  }
}
