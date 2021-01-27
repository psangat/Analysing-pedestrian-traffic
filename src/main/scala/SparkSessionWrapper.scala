import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.StructType

trait SparkSessionWrapper extends Serializable {
  // Lazy vals are useful when object creation or expression evaluation is costly in terms of CPU and memory
  // and the value may or may not be used based on some condition.
  lazy val spark: SparkSession = {
    val conf = new SparkConf()
    val configurations = Utils.readConfigFile(Constants.configPath)
    for ((k: String, v:String) <- configurations)
      conf.set(k,v)

    SparkSession.builder().config(conf).getOrCreate()
  }

  /** Stops the Spark session.
   */
  def stopSparkSession(): Unit = {
    spark.stop()
  }
}
