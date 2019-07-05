package models

/**
  * Created by Duncan on Jun, 2019
  */

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Duncan on Jun, 2019
  */

@Singleton
class ShutdownRepository @Inject() (dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class ShutdownTable(tag: Tag) extends Table[Shutdown](tag, "internet_shutdowns") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def start_date = column[Option[String]]("start_date")
    def end_date = column[Option[String]]("end_date")
    def target = column[Option[String]]("target")
    def source_url = column[Option[String]]("source_url")
    def country_code = column[String]("country_code")
    def country = column[String]("country")
    def region_affected = column[Option[String]]("region_affected")
    def party_responsible = column[Option[String]]("party_responsible")
    def blackout_type = column[Option[String]]("blackout_type")
    def services_affected = column[Option[String]]("services_affected")
    def platforms_affected = column[Option[String]]("platforms_affected")
    def cause = column[Option[String]]("cause")
    def notes = column[Option[String]]("notes")
    def * = (id, start_date, end_date, target, source_url, country_code, country, region_affected, party_responsible,
    blackout_type, services_affected, platforms_affected, cause, notes) <> ((Shutdown.apply _).tupled, Shutdown.unapply)
  }
  private val shutdown = TableQuery[ShutdownTable]

  def shutdownByCountry(): Future[Seq[(String, Int)]] = db.run {
    shutdown.groupBy(p => p.country).map{case(country, group) => (country, group.map(_.id).length)}.result
  }

  def countryShutdowns(country_code: String): Future[Seq[Shutdown]] = db.run {
    shutdown.filter(_.country_code === country_code).result
  }
}
