package controllers

import javax.inject.{Inject, Singleton}
import models.FocusGroupRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, MessagesControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class DashboardController @Inject() (cc: MessagesControllerComponents) (repo: FocusGroupRepository)
                                    (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def allParticipants() = Action.async(implicit request => {
    repo.count().map { count =>
      Ok(Json.obj("status" ->"OK", "participantCount" -> count ))
    }
  })

  def groupByAgeGroup() = Action.async(implicit request => {
    repo.ageGroups().map { ageGroups =>
      Ok(Json.obj("status" -> "OK", "ageGroups" -> ageGroups))
    }
  })
}
