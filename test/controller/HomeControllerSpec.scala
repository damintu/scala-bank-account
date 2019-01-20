package controller

import io.restassured.RestAssured._
import io.restassured.matcher.RestAssuredMatchers._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.junit.Test

class HomeControllerSpec {
@Test
  def `trying rest assured in scala`(): Unit = {

     when()
      .get("/")
    .Then()
      .statusCode(200)
  }
}