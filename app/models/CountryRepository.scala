package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Duncan on Jul, 2019
  */
@Singleton
class CountryRepository @Inject() (dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class CountryTable(tag: Tag) extends Table[Country](tag, "countries") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def code = column[String]("code")
    def name = column[String]("name")
    def * = (id, code, name) <> ((Country.apply _).tupled, Country.unapply)
  }

  private val country = TableQuery[CountryTable]

  def countryCode(name: String): Future[Option[String]] = db.run {
    country.filter(_.name === name).map(p => p.code).result.headOption
  }
}
