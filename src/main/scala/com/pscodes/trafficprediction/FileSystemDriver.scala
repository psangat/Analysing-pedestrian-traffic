package com.pscodes.trafficprediction

import com.pscodes.trafficprediction.etl.EtlDriver
import com.pscodes.trafficprediction.serving.ServingDriver
import com.pscodes.trafficprediction.training.ETLAndTrainingDriver

object FileSystemDriver extends App {
  EtlDriver.main(Array())
  ETLAndTrainingDriver.main(Array())
  ServingDriver.main(Array())
}
