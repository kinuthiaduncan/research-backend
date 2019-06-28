package models

/**
  * Created by Duncan on Jun, 2019
  */
import play.api.libs.json.Json

case class Shutdown (id: Long, start_date: String, end_date: String, target: String, source_url: String, country: String,
                     country_code: String, region_affected: String, party_responsible: String, blackout_type: String, services_affected: String,
                     platforms_affected: String, cause: String, notes: String)

object Shutdown{
  implicit val shutdownFormat = Json.format[Shutdown]
}
