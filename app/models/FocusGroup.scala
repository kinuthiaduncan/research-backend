package models

/**
  * Created by Duncan on Jun, 2019
  */

import play.api.libs.json._


case class FocusGroup (id: Long, country: String, technical_group: String, age_group: String, challenges_faced: String,
                       connected_anonymously: String, anonymity_reasons: String, anonymity_tools: String, used_VPN: String,
                       vpn_use_reason: String, gender: String, used_smart_dns: String, smart_dns_reason: String, preference: String,
                       shopping: Int, social_media: Int, entertainment: Int, news: Int, educational_research: Int, working: Int,
                       comments: String, vpn_challenges: String)

object FocusGroup{
  implicit val focusGroupFormat = Json.format[FocusGroup]
}
