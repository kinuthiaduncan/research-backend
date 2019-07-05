package models

import play.api.libs.json.Json

/**
  * Created by Duncan on Jul, 2019
  */
case class Country(id: Long, code: String, name: String)

object Country{
  implicit val countryFormat = Json.format[Country]
}