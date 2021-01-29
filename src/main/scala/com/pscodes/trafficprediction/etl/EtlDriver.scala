package com.pscodes.trafficprediction.etl

import com.pscodes.trafficprediction.common.debug.DataFrameDescriptor
import com.pscodes.trafficprediction.common.spark.SparkSessionManager
import com.pscodes.trafficprediction.etl.pedestriancount.melbourne.MelbournePedestrianCountLoader
import com.pscodes.trafficprediction.etl.sensorlocations.melbourne.MelbourneSensorsLocationLoader

object EtlDriver extends App {

  val spark = SparkSessionManager.session

  val pedestrianCount = MelbournePedestrianCountLoader(spark).load()
  DataFrameDescriptor().describe(pedestrianCount)

  val sensorLocation = MelbourneSensorsLocationLoader(spark).load()
  DataFrameDescriptor().describe(sensorLocation)
}
