package controllers

/**
  * Created by Duncan on Jun, 2019
  */

import javax.inject.{Inject, Singleton}
import models.{CensoredPlanetRepository, FocusGroupRepository}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import play.api.Configuration
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CensoredPlanetController @Inject() (scc: SecuredControllerComponents) (repo: FocusGroupRepository)
                                         (implicit ec: ExecutionContext, conf: Configuration) extends SecuredController(scc) {

  def index = Action.async {
    val conf = new SparkConf().setAppName("Word Count").setMaster("spark://10.30.20.214:7077")
      .set("spark.driver.allowMultipleContexts", "true")
    val sc = new SparkContext(conf)
    val spark = SparkSession.builder.config(sc.getConf).getOrCreate()
    println(spark)
    val textFile = sc.textFile("data/test.txt")
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    System.out.println("Total words: " + counts.count())
    Future(Ok("That was easy!"))
  }
}
