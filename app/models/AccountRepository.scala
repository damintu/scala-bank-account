package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class AccountRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class AccountTable(tag: Tag) extends Table[Account](tag, "ACCOUNT") {

    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def firstName = column[String]("FIRSTNAME")
    def lastName = column[String]("LASTNAME")
    def * = (id, firstName, lastName) <> ((Account.apply _).tupled, Account.unapply)
  }

  private val accountTable = TableQuery[AccountTable]

  def create(firstName: String, lastName: String): Future[Account] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (accountTable.map(a => (a.firstName, a.lastName))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning accountTable.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((names, id) => Account(id, names._1, names._2))
      // And finally, insert the person into the database
      ) += (firstName, lastName)
  }

  def list(): Future[Seq[Account]] = db.run {
    accountTable.result
  }
}
