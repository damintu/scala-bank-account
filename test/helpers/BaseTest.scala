package helpers

import io.restassured.RestAssured
import org.junit.BeforeClass

class BaseTest {
  @BeforeClass def setup(): Unit = {
    val port = System.getProperty("server.port")
    if (port == null) RestAssured.port = Integer.valueOf(9000)
    else RestAssured.port = Integer.valueOf(port)
    var baseHost = System.getProperty("server.host")
    if (baseHost == null) baseHost = "http://localhost"
    RestAssured.baseURI = baseHost
  }
}