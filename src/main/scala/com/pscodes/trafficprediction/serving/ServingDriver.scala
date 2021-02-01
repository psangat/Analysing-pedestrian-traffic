package com.pscodes.trafficprediction.serving

import com.pscodes.trafficprediction.common.spark.SparkSessionManager
import com.pscodes.trafficprediction.serving.classifier.spark.DTPedestrianCountClassifier
import org.apache.spark.sql.DataFrame

object ServingDriver extends App {
//
//  val spark = SparkSessionManager.session
//  val data: DataFrame = spark.createDataFrame(Array[String](
//    "2887628,11/01/2019 05:00:00 PM,2019,November,1,Friday,17,34,Flinders St-Spark La,300",
//    "2887629,11/01/2019 05:00:00 PM,2019,November,1,Friday,17,39,Alfred Place,604",
//    "2887630,11/01/2019 05:00:00 PM,2019,November,1,Friday,17,37,Lygon St (East),216",
//    "2887631,11/01/2019 05:00:00 PM,2019,November,1,Friday,17,40,Lonsdale St-Spring St (West),627"))
//
//  val dtClassifier = DTPedestrianCountClassifier(, spark).classify(data)
//  dtClassifier.show(5)

}
