import org.apache.spark.rdd.RDD

object SparkExtensions {
  implicit class SparkRDD(val rdd: RDD[String]) extends AnyVal {
    def header: String = rdd.first()
    def removeHeader(): RDD[String] = rdd.filter(line => line != header)
  }

}
