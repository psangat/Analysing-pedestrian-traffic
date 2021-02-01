package com.pscodes.trafficprediction.serving.classifier.spark

import com.pscodes.trafficprediction.serving.classifier.PedestrianCountClassifier
import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.{DataFrame, SparkSession}

class DTPedestrianCountClassifier(val model: PipelineModel, spark:SparkSession) extends PedestrianCountClassifier {

  override def classify(data: DataFrame): DataFrame = {
    // Make predictions.
    val predictions = model.transform(data)

    // Select example rows to display.
    predictions.select("predictedLabel", "aboveThreshold", "features")
  }
}

object DTPedestrianCountClassifier {
  def apply(model: PipelineModel, spark: SparkSession): DTPedestrianCountClassifier =
    new DTPedestrianCountClassifier(model, spark)
}
