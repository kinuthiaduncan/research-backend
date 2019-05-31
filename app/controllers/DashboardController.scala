package controllers

import javax.inject.{Inject, Singleton}
import models.FocusGroupRepository
import play.api.Configuration
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

@Singleton
class DashboardController @Inject() (scc: SecuredControllerComponents) (repo: FocusGroupRepository)
                                    (implicit ec: ExecutionContext, conf:Configuration) extends SecuredController(scc) {

}
