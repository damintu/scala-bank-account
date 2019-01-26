package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import java.sql.Timestamp
import java.time.Instant

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OperationRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val accountTableQuery = TableQuery[OperationTable]

  private class OperationTable(tag: Tag) extends Table[Operation](tag, "OPERATION") {

    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def date = column[Timestamp]("DATE")

    def nature = column[String]("NATURE")

    def amount = column[Double]("AMOUNT")

    def total = column[Double]("TOTAL")

    def accountId = column[Long]("ACCOUNT_ID")

    def account = foreignKey("ACCOUNT", accountId, accountTableQuery)(_.id)

    def * = (id, date, nature, amount, total) <> ((Operation.apply _).tupled, Operation.unapply)
  }

  private val operationTable = TableQuery[OperationTable]

  def create(accountId: Long, nature: String, amount: Double, total: Double): Future[Operation] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (operationTable.map(o => (o.nature, o.amount, o.total))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning operationTable.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((param, id) => Operation(id, new Timestamp(Instant.now.toEpochMilli), param._1, param._2, param._3))
      // And finally, insert the operation into the database
      ) += (nature, amount, total)
  }

  def list(): Future[Seq[Operation]] = db.run {
    operationTable.result
  }

  def findOneById(operationId: Long): Future[Option[Operation]] = db.run {
    operationTable.filter(_.id === operationId).take(1).result.headOption
  }

  def findLastestByAccountId(accountId: Long): Future[Option[Operation]] = db.run {
    operationTable.filter(_.accountId === accountId).sortBy(_.date.desc).take(1).result.headOption
  }
}
