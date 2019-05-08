package controllers

import javax.inject.{Inject, Singleton}
import models.FocusGroupRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, MessagesControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class FocusGroupController @Inject() (cc: MessagesControllerComponents) (repo: FocusGroupRepository)
                                     (implicit ec: ExecutionContext) extends AbstractController(cc) {
  def index() = Action.async(implicit request => {
    repo.list().map { focusGroups =>
      Ok(Json.toJson(focusGroups))
    }
  })

  def genderAgeGroups() = Action.async(implicit request => {
    repo.genderAgeGroups().map{ data =>
      Ok(Json.obj("status" ->"OK", "data" -> data ))
    }
  })

  def internetUsageByAgeGroup() = Action.async(implicit request => {
    repo.internetUsageByAgeGroup().map{ data =>
      Ok(Json.obj("status" ->"OK", "data" -> data ))
    }
  })

}
