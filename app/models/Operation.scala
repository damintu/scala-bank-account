package models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import play.api.libs.json._

//to keep it simple the total amount on the account will be stored with the operation
case class Operation(id : Long, date: Timestamp, nature : String, amount: Double, total : Double, accountId : Long)

object Operation {

  val Deposit = "deposit"
  val Withdrawal = "withdrawal"


  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }
  implicit val operationFormat = Json.format[Operation]

}


