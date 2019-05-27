package models

import org.apache.spark.{SparkConf, SparkContext}

import scala.concurrent.Future

class CensoredPlanetRepository {
  def test: Unit = {
    val conf = new SparkConf()
    conf.setMaster("http://localhost:8082")
    conf.setAppName("Word Count")
    val sc = new SparkContext(conf)
    val textFile = sc.textFile("data/shakespeare.txt")
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    counts.foreach(println)
    System.out.println("Total words: " + counts.count())
  }
}
