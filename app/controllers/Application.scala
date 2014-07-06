package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._
import views._

object Application extends Controller with Secured{

  // -- Index

  def index = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      Ok(views.html.main("Cobot Nobotics", user.name)(Widget.findAll)
      )
    }.getOrElse(Forbidden)
  }

  // -- Authentication

  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => User.authenticate(email, password).isDefined
    })
  )

  /**
   * Login page.
   */
  def login = Action { implicit request =>
    Ok(html.login(loginForm))
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => Redirect(routes.Application.index).withSession("email" -> user._1)
    )
  }

  /**
   * Logout and clean the session.
   */
  def logout = Action {
    Redirect(routes.Application.login).withNewSession.flashing(
      "success" -> "You've been logged out"
    )
  }

  /**
   * Provide implicit context to the views
   */
  def ActionWithContext(f: Context => Result) = {
    Action { request =>
      f(Context(Widget.findAll, request))
    }
  }

}




/**
 * Provide security features
 */
trait Secured {

  /**
   * Retrieve the connected user email.
   */
  private def username(request: RequestHeader) = request.session.get("email")

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.login)

  // --

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
    Action(request => f(user)(request))
  }


}
