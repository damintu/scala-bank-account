package controller

import io.restassured.RestAssured._
import io.restassured.http.ContentType
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.junit.{BeforeClass, Test}
import play.api.test.Helpers.running
import play.api.test.TestServer

class OperationControllerTest {

  def createAccount (){
    given()
      .body("{" +
        " \"firstName\" : \"damien\"," +
        "\"lastName\" : \"sarrol\"}")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      .post("http://localhost:9000/account")
  }

  @Test
  def `deposit with positive amount`() {
    running(TestServer(9000)) {
      given()
        .body("{" +
          " \"account\" : 1," +
          " \"type\" : \"deposit\"," +
          "\"amount\" : 50}")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .post("http://localhost:9000/operation")
        .Then()
        .statusCode(201)
        .body("$", hasKey("id"))
        .body("$", hasKey("date"))
        .body("$", hasKey("total"))
        .body("accountId", equalTo(1))
        .body("nature", equalTo("deposit"))
        .body("amount", equalTo(50))
    }
  }

  @Test
  def `deposit with negative amount`() {
    running(TestServer(9000)) {
      given()
        .body("{" +
          " \"account\" : 1," +
          " \"type\" : \"deposit\"," +
          "\"amount\" : -50}")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .post("http://localhost:9000/operation")
        .Then()
        .statusCode(400)
    }
  }

  @Test
  def `deposit 0`() {

    running(TestServer(9000)) {
      given()
        .body("{" +
          " \"account\" : 1," +
          " \"type\" : \"deposit\"," +
          "\"amount\" : 0}")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .post("http://localhost:9000/operation")
        .Then()
        .statusCode(400)
    }
  }


  //Withdrawal test
  @Test
  def `withdrawal with positive amount `() {
    running(TestServer(9000)) {
      given()
        .body("{" +
          " \"account\" : 1," +
          " \"type\" : \"withdrawal\"," +
          "\"amount\" : 50}")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .post("http://localhost:9000/operation")
        .Then()
        .statusCode(201)
        .body("$", hasKey("id"))
        .body("$", hasKey("date"))
        .body("$", hasKey("total"))
        .body("accountId", equalTo(1))
        .body("nature", equalTo("withdrawal"))
        .body("amount", equalTo(50))
    }
  }

  @Test
  def `withdrawal with negative amount`() {
    running(TestServer(9000)) {
      given()
        .body("{" +
          " \"account\" : 1," +
          " \"type\" : \"withdrawal\"," +
          "\"amount\" : -50}")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .post("http://localhost:9000/operation")
        .Then()
        .statusCode(400)
    }
  }

  @Test
  def `withdrawal 0`() {
    running(TestServer(9000)) {
      given()
        .body("{" +
          " \"account\" : 1," +
          " \"type\" : \"withdrawal\"," +
          "\"amount\" : 0}")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .post("http://localhost:9000/operation")
        .Then()
        .statusCode(400)
    }
  }

}
