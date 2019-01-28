package controller

import io.restassured.RestAssured._
import io.restassured.matcher.RestAssuredMatchers._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.junit.Test
import play.api.test.Helpers.running
import play.api.test.TestServer

class HomeControllerSpec {

  @Test
   def `trying rest assured in scala`() {
     running(TestServer(9000)) {
       given()
       when()
         .get("http://localhost:9000")
         .Then()
         .statusCode(200)
     }
   }
}