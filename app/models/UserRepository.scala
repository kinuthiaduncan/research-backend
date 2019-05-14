package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import slick.jdbc.JdbcProfile
import com.github.t3hnar.bcrypt._

import scala.collection.mutable.Map
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def first_name = column[String]("first_name")
    def last_name = column[String]("last_name")
    def email_address = column[String]("email_address")
    def password = column[String]("password")
    def * = (id, first_name, last_name, email_address, password) <> ((User.apply _).tupled, User.unapply)
  }
  private val user = TableQuery[UserTable]

  def searchUser(email_address: String, password: String): Future[Option[User]] = db.run {
    user.filter(_.email_address === email_address).result
      .map(_.headOption.filter(user => password.isBcrypted(user.password)))
  }

  def createUser(first_name: String, last_name: String, email_address: String, password: String): Future[User] = db.run {
    (user.map(p => (p.first_name, p.last_name, p.email_address, p.password))
      returning user.map(_.id)
      into ((firstLastEmailPassword, id) => User(id, firstLastEmailPassword._1, firstLastEmailPassword._2,
      firstLastEmailPassword._3, firstLastEmailPassword._4))
      ) += (first_name, last_name, email_address, password.bcrypt)
  }
}
