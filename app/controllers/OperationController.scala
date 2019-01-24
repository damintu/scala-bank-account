package controllers

import java.sql.Timestamp
import java.util.Calendar

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.data._
import play.api.data.Forms._
import models.{Operation, OperationRepository}
import play.api.libs.json._
import play.api.data.format.Formats._

import scala.concurrent.{ExecutionContext, Future}

class OperationController @Inject()(c : ControllerComponents,repository : OperationRepository)(implicit ec: ExecutionContext) extends AbstractController(c){

  case class CreateOperationForm(accountId : Long, nature : String, amount : Double)
  implicit val formOperationWrites = Json.writes[CreateOperationForm]


  implicit object FormErrorWrites extends Writes[FormError] {
    override def writes(o: FormError): JsValue = Json.obj(
      "key" -> Json.toJson(o.key),
      "message" -> Json.toJson(o.message)
    )
  }

  val operationForm = Form(
    mapping(
      "account" -> longNumber,
      "type" -> text,
      "amount" -> of(doubleFormat),
    )(CreateOperationForm.apply)(CreateOperationForm.unapply)
  )

  /**
    * A REST endpoint that creates an operation
    */
  def create = Action.async(parse.json){ implicit request =>
    operationForm.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(Json.toJson(formWithErrors.errors)))
      },
      operationForm => {
        repository.create(operationForm.accountId,operationForm.nature, operationForm.amount).map { operation =>
          Created(Json.toJson(operation))
        }
      }
    )
  }

  /**
    * A REST endpoint that gets all the operations as JSON.
    */
  def get(accountId :String ) = Action.async { implicit request =>
    repository.list().map { operation =>
      Ok(Json.toJson(operation))
    }
  }
}
