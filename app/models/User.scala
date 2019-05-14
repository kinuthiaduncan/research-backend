package models

import play.api.libs.json.Json

case class User (id: Long, first_name: String, last_name: String, email_address: String, user_level: String, password: String){
  def isAdmin: Boolean = user_level.toLowerCase == "admin"
}

object User{
  implicit val userFormat = Json.format[User]
}
