package controllers

import javax.inject.Inject
import models.{FocusGroupRepository, UserRepository}
import play.api.libs.json.Json
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent.{ExecutionContext, Future}

class AdminController @Inject() (cc: MessagesControllerComponents) (repo: FocusGroupRepository) (userRepo: UserRepository)
                                (implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val userForm: Form[CreateUserForm] = Form {
    mapping(
      "first_name" -> nonEmptyText,
      "last_name" -> nonEmptyText,
      "email_address" ->nonEmptyText,
      "password" -> nonEmptyText
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  def storeUser = Action.async(implicit request => {
    userForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok("error"))
      },
    user => {
      userRepo.createUser(user.first_name, user.last_name, user.email_address, user.password).map { _ =>
        Ok(Json.obj("status" -> "OK", "message" -> "User Saved!!"))
      }
    }
    )
  })

}
case class CreateUserForm(first_name: String, last_name: String, email_address: String, password: String)