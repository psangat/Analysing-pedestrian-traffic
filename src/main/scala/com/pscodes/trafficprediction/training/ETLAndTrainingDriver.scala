package com.pscodes.trafficprediction.training

import com.pscodes.trafficprediction.common.debug.DataFrameDescriptor
import com.pscodes.trafficprediction.common.spark.SparkSessionManager
import com.pscodes.trafficprediction.etl.pedestriancount.melbourne.MelbournePedestrianCountLoader
import com.pscodes.trafficprediction.training.spark.DTPedestrianCountClassifierTrainer

object ETLAndTrainingDriver extends App {
  val data = MelbournePedestrianCountLoader(SparkSessionManager.session).load()
  DataFrameDescriptor().describe(data)
  val model = DTPedestrianCountClassifierTrainer().train(data)
}
