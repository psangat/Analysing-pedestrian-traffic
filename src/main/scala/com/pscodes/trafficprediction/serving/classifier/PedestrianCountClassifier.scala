package com.pscodes.trafficprediction.serving.classifier

import org.apache.spark.sql.DataFrame

trait PedestrianCountClassifier {

  def classify(data: DataFrame): DataFrame

}
