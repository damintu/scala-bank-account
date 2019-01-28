package controller

import io.restassured.RestAssured._
import io.restassured.http.ContentType
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.junit.Test
import play.api.test._
import play.api.test.Helpers._

class AccountControllerTest {
  @Test
  def `accountCreationSuccessTest`() {
    running(TestServer(9000)) {
      given()
        .body("{" +
          " \"firstName\" : \"damien\"," +
          "\"lastName\" : \"sarrol\"}")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .post("http://localhost:9000/account")
        .Then()
        .statusCode(201)
        .body("$", hasKey("id"))
        .body("firstName", equalTo("damien"))
        .body("lastName", equalTo("sarrol"))
    }
  }

  @Test
  def `accountCreationFailedTest`() {
    running(TestServer(9000)) {
      given()
        .body("{" +
          " \"firstName\" : \"damien\"")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .post("http://localhost:9000/account")
        .Then()
        .statusCode(400)
    }
  }
}
