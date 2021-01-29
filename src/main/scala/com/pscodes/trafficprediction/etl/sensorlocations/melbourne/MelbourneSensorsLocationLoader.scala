package com.pscodes.trafficprediction.etl.sensorlocations.melbourne

import com.pscodes.trafficprediction.common.config.Configuration
import com.pscodes.trafficprediction.common.feeds.sensorlocations.SensorLocationsColumnNames
import com.pscodes.trafficprediction.etl.sensorlocations.SensorLocationsLoader
import org.apache.spark.sql.{DataFrame, SparkSession, functions => f}

/**
 * Implementation of the sensor locations that reads and transforms Melbourne Data.
 */
class MelbourneSensorsLocationLoader (reader: MelbourneSensorLocationReader, transformer: MelbourneSensorLocationsTransformer) extends SensorLocationsLoader{
  override def load(): DataFrame = {
    val rawSensorLocations = reader.read()
    transformer.transform(rawSensorLocations)
  }
}

object MelbourneSensorsLocationLoader{
  def apply (spark: SparkSession): MelbourneSensorsLocationLoader =
    new MelbourneSensorsLocationLoader(new MelbourneSensorLocationReader(spark), new MelbourneSensorLocationsTransformer)
}

class MelbourneSensorLocationReader(spark: SparkSession){
  def read(): DataFrame =
    spark.read
      .option("header", true)
      .option("inferSchema", true)
      .csv(Configuration().MelbourneSensorLocationsRawDataPath)
}

class MelbourneSensorLocationsTransformer{
  def transform(rawSensorLocations: DataFrame): DataFrame =
    rawSensorLocations
      .drop(f.col(SensorLocationsColumnNames.LOCATION))
      .withColumn(SensorLocationsColumnNames.LOCATION, f.array(f.col(SensorLocationsColumnNames.LATITUDE), f.col(SensorLocationsColumnNames.LONGITUDE)))
      .withColumn("castedInstallationDate", f.to_date(f.col( SensorLocationsColumnNames.INSTALLATION_DATE), "yyyy/MM/dd"))
      .drop(SensorLocationsColumnNames.INSTALLATION_DATE)
      .withColumnRenamed("castedInstallationDate", SensorLocationsColumnNames.INSTALLATION_DATE)

}
