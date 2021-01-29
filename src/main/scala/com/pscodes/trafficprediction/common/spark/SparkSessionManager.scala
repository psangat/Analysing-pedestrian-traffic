package com.pscodes.trafficprediction.common.spark

import com.pscodes.trafficprediction.common.config.Configuration
import org.apache.spark.sql.SparkSession

/**
 * Utility object managing the creation and provisioning of a spark session.
 */
object SparkSessionManager {

  lazy val session: SparkSession = {
    if (Configuration().LocalSpark) {
      local
    }
    else {
      provided
    }
  }
  private lazy val local: SparkSession = SparkSession.builder().appName("Traffic Prediction").master("local[*]").getOrCreate()
  private lazy val provided: SparkSession = SparkSession.builder().getOrCreate()
}
