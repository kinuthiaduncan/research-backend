package models

/**
  * Created by Duncan on Jun, 2019
  */
import play.api.libs.json.Json

case class Shutdown (id: Long, start_date: Option[String], end_date: Option[String], target: Option[String],
                     source_url: Option[String], country_code: String, country: String, region_affected: Option[String],
                     party_responsible: Option[String], blackout_type: Option[String], services_affected: Option[String],
                     platforms_affected: Option[String], cause: Option[String], notes: Option[String])

object Shutdown{
  implicit val shutdownFormat = Json.format[Shutdown]
}
