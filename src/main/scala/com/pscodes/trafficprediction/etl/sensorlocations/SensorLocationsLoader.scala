package com.pscodes.trafficprediction.etl.sensorlocations

import org.apache.spark.sql.DataFrame

trait SensorLocationsLoader {
  /**
   * @return sensor locations as a spark dataframe
   */
  def load():DataFrame
}
