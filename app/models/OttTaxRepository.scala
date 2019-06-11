package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OttTaxRepository @Inject() (dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class OttTaxTable(tag: Tag) extends Table[OttTax](tag, "ott_tax") {
    def id = column[Long]("id",  O.PrimaryKey, O.AutoInc)
    def gender = column[String]("gender")
    def age_group = column[String]("gender")
    def social_media_since_ott = column[String]("social_media_since_ott")
    def access_ott = column[String]("access_ott")
    def access_vpn = column[String]("access_vpn")
    def access_hotspots = column[String]("access_hotspots")
    def social_media_six_months = column[String]("social_media_six_months")
    def ott_effect_business = column[String]("ott_effect_business")
    def ott_inconvenience = column[Option[String]]("ott_inconvenience")
    def mobile_money_tax_inconvenience = column[Option[String]]("mobile_money_tax_inconvenience")
    def tax_effect_mobile_money_use = column[String]("tax_effect_mobile_money_use")
    def mobile_tax_effect_business = column[String]("mobile_tax_effect_business")
    def mobile_tax_opinion = column[String]("mobile_tax_opinion")
    def ott_tax_opinion = column[String]("ott_tax_opinion")
    def * = (id, gender, age_group, social_media_since_ott, access_ott, access_vpn, access_hotspots,
    social_media_six_months, ott_effect_business, ott_inconvenience, mobile_money_tax_inconvenience,
    tax_effect_mobile_money_use, mobile_tax_effect_business, mobile_tax_opinion, ott_tax_opinion) <>
      ((OttTax.apply _).tupled, OttTax.unapply)
  }
  private val ottTax = TableQuery[OttTaxTable]

  def list(): Future[Seq[OttTax]] = db.run {
    ottTax.result
  }

  def count(): Future[Int] = db.run {
    ottTax.length.result
  }

  def socialMediaSix(): Future[Int] = db.run {
    ottTax.filter(_.social_media_six_months === "Yes").length.result
  }

  def socialMediaSince(): Future[Int] = db.run {
    ottTax.filter(_.social_media_since_ott === "Yes").length.result
  }

  def usedVpn(): Future[Int] = db.run {
    ottTax.filter(_.access_vpn === "I use VPN").length.result
  }

  def usedHotspots(): Future[Int] = db.run {
    ottTax.filter(_.access_hotspots === "I use WiFi / Hotspot(s)").length.result
  }

  def paidOtt(): Future[Int] = db.run {
    ottTax.filter(_.access_ott === "I paid the OTT tax").length.result
  }

  def ottInconvenience(): Future[Seq[(Option[String], Int)]] = db.run {
    ottTax.filter(_.ott_inconvenience.isDefined).groupBy(p => p.ott_inconvenience)
      .map{case (inconvenience, group) => (inconvenience, group.map(_.id).length)}.sortBy(_._2)
      .result
  }

  def mobileTaxInconvenience(): Future[Seq[(Option[String], Int)]] = db.run {
    ottTax.filter(_.mobile_money_tax_inconvenience.isDefined).groupBy(p => p.mobile_money_tax_inconvenience)
      .map{case (inconvenience, group) => (inconvenience, group.map(_.id).length)}.sortBy(_._2)
      .result
  }

}
