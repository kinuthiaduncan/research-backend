package models

import play.api.libs.json.Json

case class User (id: Long, first_name: String, last_name: String, email_address: String, password: String){
  def isAdmin: Boolean = first_name.toLowerCase == "duncan"
}

object User{
  implicit val userFormat = Json.format[User]
}
