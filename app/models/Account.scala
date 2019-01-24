package models

import play.api.libs.json._

case class Account(id : Long, firstName : String, lastName : String)

object Account {
  implicit val accountFormat = Json.format[Account]
}
