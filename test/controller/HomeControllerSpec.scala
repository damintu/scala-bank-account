package controller

import io.restassured.RestAssured._
import io.restassured.matcher.RestAssuredMatchers._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._
import org.junit.Test

class HomeControllerSpec {

   @Test
   def `trying rest assured in scala`() {

     when()
       //@TODO path should be '/' but it's currently not working for me
      .get("http://localhost:9000")
    .Then()
      .statusCode(200)
  }
}