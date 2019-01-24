package models

import java.sql.Timestamp
import java.text.SimpleDateFormat
import play.api.libs.json._
import java.util.Date
case class Operation(id : Long, date: Date = new Date, nature : String, amount: Double)

object Operation {

  implicit val operationFormat = Json.format[Operation]

  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }
}


