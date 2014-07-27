package controllers

import org.squeryl.PrimitiveTypeMode.{TimestampType}
import models.Client
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}


/**
 * Created by pingvin on 7/19/14.
 */
object Clients extends  Controller {

  private val clientForm: Form[Client] = Form(
    mapping(
      "id" -> ignored(-1L),
      "username" -> nonEmptyText,
      "firstname" -> nonEmptyText,
      "surname" -> nonEmptyText(minLength = 6, maxLength = 10),
      "birthday" -> date
    )(Client.apply)(Client.unapply)
  )

  private val clientPartialForm: Form[Client] = Form(
    mapping(
      "id" -> ignored(-1L),
      "username" -> ignored(""),
      "firstname" -> nonEmptyText,
      "surname" -> ignored(""),
      "birthday" -> date
    )(Client.apply)(Client.unapply)
  )

  def list = Action {
    val clients = models.Client.findAll.toList
    Ok(views.html.client.list(clients))
  }

  def create = Action { implicit  request =>
    request.method match {
      case "POST" => {
        clientForm.bindFromRequest.fold(
          formWithErrors => {
            Ok(views.html.client.create(formWithErrors))
          },
          value => {
            Client.insert(value)
            Ok(views.html.client.show(value))
          }
        )
      }
      case "GET" => {
        Ok(views.html.client.create(clientForm))
      }
    }
  }

  def edit(id: Long) = Action { implicit request =>
    request.method match {
      case "POST" => {
        clientForm.bindFromRequest.fold(
          formWithErrors => {
            Ok(views.html.client.edit(formWithErrors, id))
          },
          value => {
            Client.findById(id).map{ client =>
              val temp = value.copy(id)
              Client.update(temp)
              Ok(views.html.client.show(temp))
            }.getOrElse(NotFound)
          }
        )
      }
      case "GET" => {
        Client.findById(id).map{ client =>
          Ok(views.html.client.edit(clientForm.fill(client), id))
        }.getOrElse(NotFound)
      }
    }
  }

  def editPassword(id: Long) = Action { implicit request =>
    Client.findById(id).map{ client =>
      request.method match {
        case "POST" => {
          clientPartialForm.bindFromRequest.fold(
            formWithErrors => {
              Ok(views.html.client.editPassword(formWithErrors, id))
            },
            value => {
                val temp = value.copy(id, client.username, value.firstname, client.surname, client.birthday)
                Client.update(temp)
                Ok(views.html.client.show(temp))
            }
          )
        }
        case "GET" => {
            Ok(views.html.client.editPassword(clientPartialForm.fill(client), id))
        }
      }
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action {
    Client.findById(id).map{ client =>
      Client.delete(client)
      Ok(views.html.client.list(Client.findAll.toList))
    }.getOrElse(NotFound)
  }

  def show(id: Long) = Action { implicit request =>
      Client.findById(id).map{ client =>
        Ok(views.html.client.show(client))
      }.getOrElse(NotFound)
  }

}

