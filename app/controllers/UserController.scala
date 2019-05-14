package controllers

import javax.inject.Inject
import models.UserRepository
import models.User
import pdi.jwt.JwtSession
import pdi.jwt.JwtSession._
import play.api.Configuration
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.MessagesControllerComponents

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class UserController @Inject() (scc: SecuredControllerComponents)  (cc: MessagesControllerComponents)
                               (userRepo: UserRepository)
                              (implicit ec: ExecutionContext, conf:Configuration) extends SecuredController(scc) {

//  private val loginForm: Reads[(String, String)] =
//    ((JsPath \ "email_address").read[String] and (JsPath \ "password").read[String]).tupled
val loginForm: Form[LoginForm] = Form {
  mapping(
    "email_address" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginForm.apply)(LoginForm.unapply)
}

  def login = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok("error"))
      },
      user => {
        userRepo.searchUser(user.email_address, user.password).map { user =>
          if(user.nonEmpty) {
            var session = JwtSession()
            session = session + ("user", User(user.get.id,
              user.get.first_name,
              user.get.last_name,
              user.get.email_address,
              user.get.password))
            val token = session.serialize
            val userDetails = session.get("user")
            Ok(Json.obj("status" -> "OK", "user" -> userDetails, "token" -> token))
          }
          else
            Unauthorized
        }
      }
    )
  }

  def publicApi = Action.async {
    Future(Ok("That was easy!"))
  }

  def privateApi = AuthenticatedAction.async {
    Future(Ok("Only the best can see that."))
  }

  def adminApi = AdminAction.async {
    Future(Ok("Top secret data. Hopefully, nobody will ever access it."))
  }

}
case class LoginForm(email_address: String, password: String)