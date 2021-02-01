package com.pscodes.trafficprediction.etl

import com.pscodes.trafficprediction.common.debug.DataFrameDescriptor
import com.pscodes.trafficprediction.common.spark.SparkSessionManager
import com.pscodes.trafficprediction.etl.pedestriancount.melbourne.MelbournePedestrianCountLoader
import com.pscodes.trafficprediction.etl.sensorlocations.melbourne.MelbourneSensorsLocationLoader
import org.apache.log4j.{Level, Logger}

object EtlDriver extends App {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  val spark = SparkSessionManager.session

  val pedestrianCount = MelbournePedestrianCountLoader(spark).load()
  DataFrameDescriptor().describe(pedestrianCount)

  val sensorLocation = MelbourneSensorsLocationLoader(spark).load()
  DataFrameDescriptor().describe(sensorLocation)
}
