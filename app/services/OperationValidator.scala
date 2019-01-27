package services

import javax.inject.Inject
import models.OperationRepository
import models.Operation

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success}

class OperationValidator @Inject()(repository: OperationRepository) (implicit ec: ExecutionContext) {

  def processAmount(accountId: Long, nature: String, amount: Double): Future[Double] = for {
    operation <- repository findLastestByAccountId accountId;
    result <- calculateAmount(operation, nature, amount)
  } yield result

  @throws
  private def calculateAmount(previousOperation: Option[Operation], nature: String, amount: Double): Future[Double] = {
    Future(previousOperation match {
      case Some(prevOp) => nature match {
        case Operation.Deposit => prevOp.total + amount
        case Operation.Withdrawal => prevOp.total - amount
      }
      case None => amount
    })
  }
}