package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.data._
import play.api.data.Forms._
import models.{Operation, OperationRepository}
import play.api.libs.json._
import play.api.data.format.Formats._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import services.OperationProcessor
import scala.concurrent.{ExecutionContext, Future}

class OperationController @Inject()(
                                     c: ControllerComponents,
                                     repository: OperationRepository,
                                     op: OperationProcessor,
                                     ac : AccountController
                                   )(implicit ec: ExecutionContext) extends AbstractController(c) {

  /**
    * A REST endpoint that creates an operation
    */
  def create = Action.async(parse.json) { implicit request =>
    operationForm.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(Json.toJson(formWithErrors.errors)))
      },
      operationForm => {
        startApiProcessAmountCall(operationForm)
          .map{operation =>
            Created(Json.toJson(operation))
          }
      }
    )
  }

  private def startApiProcessAmountCall(form: CreateOperationForm) = for (
    processAmount <- op.processAmount(form.accountId,form.nature,form.amount);
    create <- repository.create(form.accountId,form.nature,form.amount,processAmount)
  ) yield create

  /**
    * A REST endpoint that gets all the operations as JSON.
    */
  def list = Action.async { implicit request =>
    repository.list().map { operation =>
      Ok(Json.toJson(operation))
    }
  }

  def get(operationId: Long) = Action.async { implicit request =>
    repository.findOneById(operationId).map { operation =>
      Ok(Json.toJson(operation))
    }
  }

  //@todo All of this has nothing to do in the controller.

  implicit val formOperationWrites = Json.writes[CreateOperationForm]

  case class CreateOperationForm(accountId: Long, nature: String, amount: Double)

  implicit object FormErrorWrites extends Writes[FormError] {
    override def writes(o: FormError): JsValue = Json.obj(
      "key" -> Json.toJson(o.key),
      "message" -> Json.toJson(o.message)
    )
  }

  implicit val TypeFieldConstraint: Constraint[CreateOperationForm] = Constraint("constraints.TypeFieldConstraint")({
    registerForm =>
      // you have access to all the fields in the form here and can
      // write complex logic here
      if (registerForm.nature == "deposit" || registerForm.nature == "withdrawal") {
        Valid
      } else {
        Invalid(Seq(ValidationError("\"type\" field must be \"deposit\" or \"withdrawal\"")))
      }
  })
  implicit val PositiveAmountConstraint: Constraint[CreateOperationForm] = Constraint("constraints.PositiveAmountConstraint")({
    registerForm =>
      // you have access to all the fields in the form here and can
      // write complex logic here
      if (registerForm.amount > 0) {
        Valid
      } else {
        Invalid(Seq(ValidationError("\"amount\" field must be positive")))
      }
  })

  val operationForm = Form(
    mapping(
      "account" -> longNumber,
      "type" -> text,
      "amount" -> of(doubleFormat),
    )(CreateOperationForm.apply)(CreateOperationForm.unapply)
      .verifying(TypeFieldConstraint)
      .verifying(PositiveAmountConstraint)
  )


}
