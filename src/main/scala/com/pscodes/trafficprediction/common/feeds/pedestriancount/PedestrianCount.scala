package com.pscodes.trafficprediction.common.feeds.pedestriancount

case class PedestrianCount( ID : Long,
                            Date_Time: String,
                            Year: Int,
                            Month:String,
                            Mdate: Int,
                            Day: String,
                            Time: Int,
                            Sensor_ID: Int,
                            Sensor_Name: String,
                            Hourly_Counts: Int)

