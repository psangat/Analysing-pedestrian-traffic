package com.pscodes.trafficprediction.training.spark

import com.pscodes.trafficprediction.common.feeds.pedestriancount.PedestrianCountColumnNames
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.{DataFrame, functions => f}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.DateType

class DTPedestrianCountClassifierTrainer {

  private val featuresColumns = Array[String](PedestrianCountColumnNames.YEAR,
    PedestrianCountColumnNames.MDATE,
    PedestrianCountColumnNames.TIME,
    PedestrianCountColumnNames.SENSORID,
    PedestrianCountColumnNames.HOURLYCOUNTS,
    "week",
    "month",
    "dayOfWeek",
    "lastHourCount",
    "yesterdayCount")
  private lazy val va = new VectorAssembler()
    .setInputCols(featuresColumns)
    .setOutputCol("features")
    .setHandleInvalid("skip")

  private lazy val dt = new DecisionTreeClassifier()
    .setLabelCol("aboveThreshold")
    .setFeaturesCol("indexedFeatures")

  private lazy val pipeline = new Pipeline()
    .setStages(Array(va, dt))

  def train(pedestrianCountTrainingData: DataFrame) = pipeline.fit(trainingData(pedestrianCountTrainingData))

  private def trainingData(pedestrianCountTrainingData: DataFrame): DataFrame = {
    val windowByDay = Window.partitionBy(PedestrianCountColumnNames.SENSORID,
      PedestrianCountColumnNames.YEAR,
      PedestrianCountColumnNames.MONTH,
      PedestrianCountColumnNames.MDATE)
      .orderBy(PedestrianCountColumnNames.TIME)

    val windowByHour = Window.partitionBy(PedestrianCountColumnNames.SENSORID,
      PedestrianCountColumnNames.TIME)
      .orderBy("date")

    val trainingData = pedestrianCountTrainingData
      .withColumn("date", f.col(PedestrianCountColumnNames.DATETIME).cast(DateType))
      .withColumn("lastHourCount", f.lag(f.col(PedestrianCountColumnNames.HOURLYCOUNTS), 1).over(windowByHour))
      .withColumn("yesterdayCount", f.lag(f.col(PedestrianCountColumnNames.HOURLYCOUNTS), 1).over(windowByDay))
      .withColumn("week", f.weekofyear(f.col(PedestrianCountColumnNames.DATETIME)))
      .withColumn("month", f.month(f.col(PedestrianCountColumnNames.DATETIME)))
      .withColumn("dayOfWeek", f.dayofweek(f.col(PedestrianCountColumnNames.DATETIME)) - 1)
      .withColumn("dayOfWeek", f.when(f.col("dayOfWeek") === 0, 7).otherwise(f.col("dayOfWeek")))
      .select(PedestrianCountColumnNames.YEAR,
        PedestrianCountColumnNames.MDATE,
        PedestrianCountColumnNames.TIME,
        PedestrianCountColumnNames.SENSORID,
        PedestrianCountColumnNames.HOURLYCOUNTS,
        "week",
        "month",
        "dayOfWeek",
        "lastHourCount",
        "yesterdayCount",
        "aboveThreshold")
      .filter(f.col(PedestrianCountColumnNames.TIME) > 8)
      .filter(f.col(PedestrianCountColumnNames.YEAR) >= 2015 and f.col(PedestrianCountColumnNames.YEAR) < 2020)
      .cache()
    trainingData
  }
}

object DTPedestrianCountClassifierTrainer {
  def apply(): DTPedestrianCountClassifierTrainer = new DTPedestrianCountClassifierTrainer
}
