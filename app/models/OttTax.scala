package models

/**
  * Created by Duncan on Jun, 2019
  */

import play.api.libs.json.Json

case class OttTax (id: Long, gender: String, age_group: String, social_media_since_ott: String, access_ott: String,
                   access_vpn: String, access_hotspots: String, social_media_six_months: String,
                   ott_effect_business: String, ott_inconvenience: Option[String], mobile_money_tax_inconvenience: Option[String],
                   tax_effect_mobile_money_use: String, mobile_tax_effect_business: String, mobile_tax_opinion: String,
                   ott_tax_opinion: String)

object OttTax {
  implicit val ottTaxFormat = Json.format[OttTax]
}
