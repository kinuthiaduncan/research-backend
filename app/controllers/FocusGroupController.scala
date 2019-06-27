package controllers

/**
  * Created by Duncan on Jun, 2019
  */

import javax.inject.{Inject, Singleton}
import models.FocusGroupRepository
import play.api.Configuration
import play.api.libs.json.Json

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FocusGroupController @Inject() (scc: SecuredControllerComponents) (repo: FocusGroupRepository)
                                     (implicit ec: ExecutionContext, conf:Configuration) extends SecuredController(scc) {

  def index() = AuthenticatedAction.async(implicit request => {
    repo.list().map { focusGroups =>
      Ok(Json.toJson(focusGroups))
    }
  })

  def allParticipants() = AuthenticatedAction.async(implicit request => {
    repo.count().map { count =>
      Ok(Json.obj("status" -> "OK", "participantCount" -> count))
    }
  })

  def groupByAgeGroup() = AuthenticatedAction.async(implicit request => {
    val data = scala.collection.mutable.Map[String,Int]()
    repo.ageGroups().map { ageGroups =>
      ageGroups.foreach(ageGroup => {
        data(ageGroup._1) = ageGroup._2
      })
      Ok(Json.obj("status" -> "OK", "ageGroups" -> data))
    }
  })

  def genderAgeGroups() = AuthenticatedAction.async(implicit request => {
    repo.genderAgeGroups().map{ genderAgeGroups =>
      Ok(Json.obj("status" ->"OK", "genderAgeGroup" -> genderAgeGroups ))
    }
  })

  def techLevelAgeGroups() = AuthenticatedAction.async(implicit request => {
    repo.techLevelAgeGroups().map{ techAgeGroups =>
      Ok(Json.obj("status" ->"OK", "techAgeGroups" -> techAgeGroups ))
    }
  })
//graph APIs
  def internetUsageByAgeGroup() = AuthenticatedAction.async(implicit request => {
    val data = mutable.Map[String, mutable.Map[String, Option[Int]]]()
    repo.internetUsageByAgeGroup().map{ users =>
      for(user <- users){
        val tempHolder = mutable.Map[String, Option[Int]]()
        tempHolder("shopping") = user._2
        tempHolder("education") = user._3
        tempHolder("entertainment") = user._4
        tempHolder("social") = user._5
        tempHolder("news") = user._6
        tempHolder("working") = user._7
        data(user._1) = tempHolder
      }
      Ok(Json.obj("status" ->"OK", "data" -> data ))
    }
  })

  def vpnAge(condition: String) = AuthenticatedAction.async(implicit request => {
    val data = mutable.Map[String,mutable.Map[String, Int]]()
    val tempHolder = mutable.Map[String, Int]()
    repo.vpnByAgeGroups(condition).map { vpnAgeUse =>
      vpnAgeUse.foreach(item => {
        tempHolder(item._1) = item._2
        data(condition) = tempHolder
      })
      Ok(Json.obj("status" ->"OK", "data" -> data ))
    }
  })

  def smartDNSAge(condition: String) = AuthenticatedAction.async(implicit request => {
    val data = mutable.Map[String,mutable.Map[String, Int]]()
    val tempHolder = mutable.Map[String, Int]()
    repo.smartDnsByAgeGroups(condition).map{ smartDNSUse =>
      smartDNSUse.foreach(item => {
        tempHolder(item._1) = item._2
        data(condition) = tempHolder
      })
      Ok(Json.obj("status" ->"OK", "data" -> data ))
    }
  })

  def vpnUsageReason() = AuthenticatedAction.async(implicit request => {
    val data = for {
      remotely <- repo.vpnUseReason("remotely")
      geo <- repo.vpnUseReason("regions")
      identity <- repo.vpnUseReason("identity")
      restricted <- repo.vpnUseReason("restricted at work")
    } yield (remotely, geo, identity, restricted)
    data.map(item => {
      Ok(Json.obj("Working remotely" -> item._1, "Geoblocked content" -> item._2, "Hide identity" -> item._3,
        "Restricted content" -> item._4))
    })
  })

  def dnsUsageReason() = AuthenticatedAction.async(implicit request => {
    val data = for {
      remotely <- repo.dnsUseReason("remotely")
      geo <- repo.dnsUseReason("in my region")
      identity <- repo.dnsUseReason("identity")
      restricted <- repo.dnsUseReason("restricted at work")
    } yield (remotely, geo, identity, restricted)
    data.map(item => {
      Ok(Json.obj("Working remotely" -> item._1, "Geoblocked content" -> item._2, "Hide identity" -> item._3,
        "Restricted content" -> item._4))
    })
  })
}
