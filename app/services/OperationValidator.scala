package services

import javax.inject.Inject
import models.OperationRepository
import models.Operation

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success}

class OperationValidator @Inject()(repository: OperationRepository) (implicit ec: ExecutionContext) {


  def processAmount (accountId:Long, nature : String, amount: Double): Double = {

    val operation = repository findLastestByAccountId accountId
    calculateAmount(operation,nature,amount)
  }

  @throws
  private def calculateAmount(previousOperation: Future[Option[Operation]],nature : String, amount : Double): Double ={

    var total = 10.00
    previousOperation onComplete {
      case Success(o) => {
        if (o.isEmpty && nature == Operation.Deposit){
            amount
        } else if ( o.isDefined){
            if (nature == Operation.Deposit){
               o.get.total + amount
            }else if (nature == Operation.Deposit){
              o.get.total - amount
            }
        }
      }
      case Failure(e) =>
        throw new Exception(e)
    }
    total
  }
}
