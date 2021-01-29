package com.pscodes.trafficprediction.common.config

import scala.util.Try


trait Configuration {
  val LocalSpark:Boolean
  val DebugEnabled:Boolean
  val MelbourneSensorLocationsRawDataPath: String
  val MelbournePedestrianCountRawDataPath: String
}
object Configuration{
  private var instance: Configuration = ConfigurationProperties

  /**
   *
   * @return the singleton configuration instance.
   */
  def apply(): Configuration = instance

  /**
   * Forces the given configuration as the singleton instance.
   * @param configuration the configuration to be forced.
   */
  def force(configuration: Configuration): Unit = instance=configuration
}

object ConfigurationProperties extends Configuration{
  val LocalSpark:Boolean = Try(System.getenv("spark.local").equals("true")).getOrElse(false)
  val DebugEnabled: Boolean = Try(System.getenv("debug.enabled").equals("true")).getOrElse(false)
  val MelbourneSensorLocationsRawDataPath: String = System.getenv("melbourne.sensor.locations.path")
  val MelbournePedestrianCountRawDataPath: String = System.getenv("melbourne.pedestrian.count.path")
}
