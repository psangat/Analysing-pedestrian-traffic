package com.pscodes.trafficprediction.common.feeds.io

import org.apache.spark.sql.catalyst.plans.logical.CacheTableStatement
import org.apache.spark.sql.{DataFrame, SparkSession}

class CSVFeedIO(spark: SparkSession) extends FeedIO[DataFrame] {

  override def read(path: String): DataFrame = spark.read.csv(path)

  override def write(update: DataFrame, path: String): Unit = {
    update.write.csv(path)
  }
}

object CSVFeedIO{
  def apply(spark:SparkSession): CSVFeedIO = new CSVFeedIO(spark)
}
