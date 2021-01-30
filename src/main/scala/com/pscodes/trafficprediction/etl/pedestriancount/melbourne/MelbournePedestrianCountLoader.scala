package com.pscodes.trafficprediction.etl.pedestriancount.melbourne

import com.pscodes.trafficprediction.common.config.Configuration
import com.pscodes.trafficprediction.common.feeds.pedestriancount.{PedestrianCount, PedestrianCountColumnNames}
import com.pscodes.trafficprediction.etl.pedestriancount.PedestrianCountLoader
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession, functions => f}


/**
 * Implementation of the pedestrian count that reads and transforms Melbourne Data.
 */
class MelbournePedestrianCountLoader (reader: MelbournePedestrianCountReader, transformer: MelbournePedestrianCountTransformer) extends PedestrianCountLoader {
  override def load(): DataFrame = {
    val rawPedestrianCount = reader.read()
    transformer.transform(rawPedestrianCount)
  }
}

object MelbournePedestrianCountLoader{
  def apply(spark: SparkSession): MelbournePedestrianCountLoader =
    new MelbournePedestrianCountLoader(new MelbournePedestrianCountReader(spark), new MelbournePedestrianCountTransformer)
}

class MelbournePedestrianCountReader(spark: SparkSession) {
  def read(): DataFrame =
    spark.read
      .option("header", true)
      .schema(Encoders.product[PedestrianCount].schema)
      .csv(Configuration().MelbournePedestrianCountRawDataPath)
}

class MelbournePedestrianCountTransformer {

  def transform(rawPedestrianCount: DataFrame): DataFrame =
    rawPedestrianCount
      .withColumn("castedDateTime", f.to_timestamp(f.col(PedestrianCountColumnNames.DATETIME), "MM/dd/yyyy hh:mm:ss a" ))
      .drop(f.col(PedestrianCountColumnNames.DATETIME))
      .withColumnRenamed("castedDateTime", PedestrianCountColumnNames.DATETIME )
      .withColumn("above_threshold", f.when(f.col(PedestrianCountColumnNames.HOURLYCOUNTS ) < 2000,0).otherwise(1))
}
