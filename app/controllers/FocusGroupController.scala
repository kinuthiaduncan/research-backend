package controllers

import javax.inject.{Inject, Singleton}
import models.FocusGroupRepository
import play.api.Configuration
import play.api.libs.json.Json
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FocusGroupController @Inject() (scc: SecuredControllerComponents) (repo: FocusGroupRepository)
                                     (implicit ec: ExecutionContext, conf:Configuration) extends SecuredController(scc) {

  def index() = AuthenticatedAction.async(implicit request => {
    repo.list().map { focusGroups =>
      Ok(Json.toJson(focusGroups))
    }
  })

  def genderAgeGroups() = AuthenticatedAction.async(implicit request => {
    repo.genderAgeGroups().map{ data =>
      Ok(Json.obj("status" ->"OK", "data" -> data ))
    }
  })

  def internetUsageByAgeGroup() = AuthenticatedAction.async(implicit request => {
    repo.internetUsageByAgeGroup().map{ data =>
      Ok(Json.obj("status" ->"OK", "data" -> data ))
    }
  })

  def vpnAge(condition: String) = AuthenticatedAction.async(implicit request => {
    repo.vpnByAgeGroups(condition).map { data =>
      val testing = for(x <- data) yield {x._1}
      println(testing)
      Ok(Json.obj("status" ->"OK", "data" -> data ))
    }
  })
}
