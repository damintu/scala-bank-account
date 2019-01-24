package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models.Account
import play.api.libs.json._

class AccountController @Inject()(c : ControllerComponents) extends AbstractController(c){

  //implicit val accountReads = Json.reads[Account]
  implicit val accountWrites = Json.writes[Account]


  implicit object FormErrorWrites extends Writes[FormError] {
    override def writes(o: FormError): JsValue = Json.obj(
      "key" -> Json.toJson(o.key),
      "message" -> Json.toJson(o.message)
    )
  }

  val accountForm = Form(
    mapping(
      "firstName" -> text,
      "lastName" -> text,
      "id" -> optional(number)
    )(Account.apply)(Account.unapply)
  )

  def create = Action.async(parse.json){ implicit request =>
      accountForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(Json.toJson(formWithErrors.errors))
        },
        accountForm => {
          //@todo save it

          Created(Json.toJson(accountForm))
        }
      )
  }

  def get = ???
}
