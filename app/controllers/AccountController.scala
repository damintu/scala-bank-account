package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.data._
import play.api.data.Forms._
import models.{Account, AccountRepository}
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

class AccountController @Inject()(c : ControllerComponents,repository : AccountRepository)(implicit ec: ExecutionContext) extends AbstractController(c){

  case class FormAccount(firstName : String, lastName : String)
  implicit val formAccountWrites = Json.writes[FormAccount]


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
    )(FormAccount.apply)(FormAccount.unapply)
  )

  def create = Action.async(parse.json){ implicit request =>
      accountForm.bindFromRequest.fold(
        formWithErrors => {
          Future.successful(BadRequest(Json.toJson(formWithErrors.errors)))
        },
        accountForm => {
          repository.create(accountForm.firstName, accountForm.lastName).map { account =>
            Created(Json.toJson(account))
          }
        }
      )
  }

  def get = ???
}
