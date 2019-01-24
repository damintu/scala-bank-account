package controller

import io.restassured.RestAssured._
import io.restassured.http.ContentType
import io.restassured.matcher.RestAssuredMatchers._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.junit.Test

class OperationControllerTest {
  @Test
  def `deposit with positive amount`() {
    given()
      .body("{" +
        " \"account\" : 1," +
        " \"type\" : \"deposit\"," +
        "\"amount\" : 50}")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      //@TODO path should be '/' but it's currently not working for me
      .post("http://localhost:9000/operation")
      .Then()
      .statusCode(200)
      .body("$",hasKey("id"))
      .body("$",hasKey("date"))
      .body("account",equalTo(1))
      .body("type",equalTo("deposit"))
      .body("amount",greaterThan(50))
  }

  @Test
  def `deposit with negative amount`() {
    given()
      .body("{" +
        " \"account\" : 1," +
        " \"type\" : \"deposit\"," +
        "\"amount\" : -50}")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      //@TODO path should be '/' but it's currently not working for me
      .post("http://localhost:9000/operation")
      .Then()
      .statusCode(400)
  }

  @Test
  def `deposit 0`() {
    given()
      .body("{" +
        " \"account\" : 1," +
        " \"type\" : \"deposit\"," +
        "\"amount\" : 0}")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      //@TODO path should be '/' but it's currently not working for me
      .post("http://localhost:9000/operation")
      .Then()
      .statusCode(400)
  }
//Withdrawal test

  @Test
  def `withdrawal with positive amount `() {
    given()
      .body("{" +
        " \"account\" : 1," +
        " \"type\" : \"withdrawal\"," +
        "\"amount\" : 50}")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      //@TODO path should be '/' but it's currently not working for me
      .post("http://localhost:9000/operation")
      .Then()
      .statusCode(200)
      .body("$",hasKey("id"))
      .body("$",hasKey("date"))
      .body("account",equalTo(1))
      .body("type",equalTo("withdrawal"))
      .body("amount",greaterThan(50))
  }

  @Test
  def `withdrawal with negative amount`() {
    given()
      .body("{" +
        " \"account\" : 1," +
        " \"type\" : \"withdrawal\"," +
        "\"amount\" : -50}")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      //@TODO path should be '/' but it's currently not working for me
      .post("http://localhost:9000/operation")
      .Then()
      .statusCode(400)
  }

  @Test
  def `withdrawal 0`() {
    given()
      .body("{" +
        " \"account\" : 1," +
        " \"type\" : \"withdrawal\"," +
        "\"amount\" : 0}")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      //@TODO path should be '/' but it's currently not working for me
      .post("http://localhost:9000/operation")
      .Then()
      .statusCode(400)
  }

}
