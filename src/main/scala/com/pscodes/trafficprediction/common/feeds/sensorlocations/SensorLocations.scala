package com.pscodes.trafficprediction.common.feeds.sensorlocations

case class SensorLocations (sensor_id: Long,
                            sensor_description: String,
                            sensor_name: String,
                            installation_date: String,
                            status: String,
                            note: String,
                            direction_1: String,
                            direction_2: String,
                            latitude: Double,
                            longitude: Double,
                            location:String)
