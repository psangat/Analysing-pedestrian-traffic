package com.pscodes.trafficprediction.etl.pedestriancount

import org.apache.spark.sql.DataFrame

trait PedestrianCountLoader {
  /**
   * @return pedestrian count as a spark dataframe
   */
  def load():DataFrame
}
