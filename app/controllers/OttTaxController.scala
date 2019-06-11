package controllers

import javax.inject.Inject
import models.OttTaxRepository
import play.api.Configuration
import play.api.libs.json.Json

import scala.collection.mutable
import scala.concurrent.ExecutionContext

class OttTaxController @Inject()(scc: SecuredControllerComponents)(repo: OttTaxRepository)
                                (implicit ec: ExecutionContext, conf: Configuration) extends SecuredController(scc) {

  def socialMediaUseEffect() = AuthenticatedAction.async(implicit request => {
    val data = for {
      afterData <- repo.socialMediaSince()
      beforeData <- repo.socialMediaSix()
    } yield (afterData, beforeData)
    data.map(item => {
      val percentageChange: Float = ((item._1.toFloat - item._2.toFloat) / item._2.toFloat) * 100
      Ok(Json.obj("beforeOtt" -> item._2, "afterOtt" -> item._1, "percentageChange" -> percentageChange))
    })
  })

  def mitigation() = AuthenticatedAction.async(implicit request => {
    val data = for {
      paidOtt <- repo.paidOtt()
      usedVpn <- repo.usedVpn()
      usedHotspots <- repo.usedHotspots()
    } yield (paidOtt, usedVpn, usedHotspots)
    data.map(item => {
      Ok(Json.obj("paidOtt" -> item._1, "usedVpn" -> item._2, "usedHotspots" -> item._3))
    })
  })

  def ottInconvenience() = AuthenticatedAction.async(implicit request => {
    val returnData = mutable.Map[String, Int]()
    val data = for {
      inconvenience <- repo.ottInconvenience()
    } yield inconvenience
    data.map{ data =>
      data.foreach(item => {
        returnData(item._1.get) = item._2
      })
      Ok(Json.obj("data" -> returnData))
    }
  })

  def mobileTaxInconvenience() = AuthenticatedAction.async(implicit request => {
    val returnData = mutable.Map[String, Int]()
    val data = for {
      inconvenience <- repo.mobileTaxInconvenience()
    }yield inconvenience
    data.map { data =>
      data.foreach(item => {
        returnData(item._1.get) = item._2
      })
      Ok(Json.obj("data" -> returnData))
    }
  })
}
