package controllers

/**
  * Created by Duncan on Jun, 2019
  */

import javax.inject.Inject
import models.{FocusGroupRepository, UserRepository}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc.MessagesControllerComponents
import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent.{ExecutionContext, Future}

class AdminController @Inject() (scc: SecuredControllerComponents) (cc: MessagesControllerComponents)
                                (repo: FocusGroupRepository) (userRepo: UserRepository)
                                (implicit ec: ExecutionContext, conf:Configuration) extends SecuredController(scc) {

  val userForm: Form[CreateUserForm] = Form {
    mapping(
      "first_name" -> nonEmptyText,
      "last_name" -> nonEmptyText,
      "email_address" ->nonEmptyText,
      "user_level" -> nonEmptyText,
      "password" -> nonEmptyText
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  def storeUser = AdminAction.async(implicit request => {
    userForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok("error"))
      },
    user => {
      userRepo.createUser(user.first_name, user.last_name, user.email_address, user.user_level, user.password).map { _ =>
        Ok(Json.obj("status" -> "OK", "message" -> "User Saved!!"))
      }
    }
    )
  })

  def getAllUsers = AdminAction.async(implicit request => {
    userRepo.list().map {users =>
      Ok(Json.obj("status" -> "OK", "users" -> users))
    }
  })

}
case class CreateUserForm(first_name: String, last_name: String, email_address: String, user_level: String, password: String)