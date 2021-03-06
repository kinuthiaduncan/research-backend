package controllers

/**
  * Created by Duncan on Jun, 2019
  */

import javax.inject.Inject
import models.{CountryRepository, ShutdownRepository}
import play.api.Configuration
import play.api.libs.json.Json

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}
import scala.io.BufferedSource
import scala.util.Failure

class CensorshipController @Inject() (scc: SecuredControllerComponents) (shutdownRepo: ShutdownRepository) (countryRepo: CountryRepository)
                                     (implicit ec: ExecutionContext, conf:Configuration) extends SecuredController(scc) {

  def interestOverTime() = AuthenticatedAction.async(implicit request => {
    val apiUrl: String = "http://nodebackend.test/google/interest_over_time"
    val geo = request.body.asFormUrlEncoded.get("geo").head
    val keyword = request.body.asFormUrlEncoded.get("keyword").head
    val startTime = request.body.asFormUrlEncoded.get("startTime").head
    val endTime = request.body.asFormUrlEncoded.get("endTime").head
    val decodedUrl = apiUrl+"?keyword="+keyword+"&geo="+geo+"&startTime="+startTime+"&endTime="+endTime
    val content: BufferedSource = scala.io.Source.fromURL(decodedUrl, "utf-8")
    val data = scala.collection.mutable.Map[String, String]()
    try {
      content.getLines().foreach(item => {
        data("results") = item
      })
    } catch {
      case ioe: java.io.IOException => Failure(ioe)
      case ste: java.net.SocketTimeoutException => Failure(ste)
    }
    Future(Ok(Json.obj("data" -> data)))
  })

  def get(url: String) = scala.io.Source.fromURL(url)

  def shutdownByCountry() = AuthenticatedAction.async(implicit request => {
    val dataHolder = mutable.Map[String, Int]()
    val data = for {
      shutdowns <- shutdownRepo.shutdownByCountry()
    } yield shutdowns
    data.map(item => {
      item.foreach(p => {
        dataHolder(p._1) = p._2
      })
      Ok(Json.obj("data" -> dataHolder))
    })
  })

  def getCountryCode(country: String) = AuthenticatedAction.async(implicit request => {
    countryRepo.countryCode(country).map(item => {
      Ok(Json.obj("data" -> item))
    })
  })

  def getCountryShutdowns(country_code: String) = AuthenticatedAction.async(implicit request => {
    shutdownRepo.countryShutdowns(country_code).map(item => {
      Ok(Json.obj("data" -> item))
    })
  })
}
