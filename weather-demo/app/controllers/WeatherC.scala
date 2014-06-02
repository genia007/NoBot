package controllers

import play.api.mvc.{Action, Controller}
import play.api._
import play.api.mvc._
import models.Weather
import scala.concurrent._
import scala.concurrent.duration._
import play.api.libs.ws._
import scala.concurrent.Future
import ExecutionContext.Implicits.global
import play.api.libs.json._
import play.api.libs.json.JsResult

object WeatherC extends Controller {
  def show = Action.async { implicit request =>
    val W_out = Weather.stubWeather

    val loc = "Pittsburgh"
    val celc = true
    val placeid = 2473224 //no longer used

    val URL = "http://api.openweathermap.org/data/2.5/find?q=" + loc + "&units=metric"

    WS.url(URL).get.map { response =>
      val humidity = (response.json \ "list" \\ "humidity")(0).as[Int]
      val temp = (response.json \ "list" \\ "temp")(0).as[Int]
      val desc = (response.json \ "list" \\ "description")(0).as[String]
/*
      System.out.println(temp)
      System.out.println(humidity)
      System.out.println(desc)
*/

      val W_out = Weather(placeid, loc, celc, temp, humidity, desc, 30)

      Ok(views.html.weather(W_out))
    }

  }
}