import org.apache.spark.storage.StorageLevel
import org.apache.spark.sql.{functions => f}
object Main extends SparkSessionWrapper {

  def loadData()={
    val pedestrianCountRDD = SparkUtils.readCSV(Constants.pedestrianDataPath,3).persist(StorageLevel.MEMORY_AND_DISK_SER_2)
    val pedestrianCountRDDHeader = SparkUtils.getHeader(pedestrianCountRDD)
    val pedestrianCountRDDWOHeader = SparkUtils.removeHeader(pedestrianCountRDD, pedestrianCountRDDHeader)
    val parsedPedestrianCountRDDWOHeader = SparkUtils.parseCSVData(pedestrianCountRDDWOHeader, new PedestrianCount())
    val hourlyCountMax = parsedPedestrianCountRDDWOHeader.map(x => x.asInstanceOf[PedestrianCount].hourlyCounts).max()

    val hourlyCountMin = parsedPedestrianCountRDDWOHeader.map(x => x.asInstanceOf[PedestrianCount].hourlyCounts).min()

    val partitionedRDD = parsedPedestrianCountRDDWOHeader.map(x => (x.asInstanceOf[PedestrianCount].hourlyCounts, x)).partitionBy(new PedestrianCountPartitioner(hourlyCountMin, hourlyCountMax, 1000, 1))
    SparkUtils.countByPartition(partitionedRDD)


    //    val sensorRDD = SparkUtils.readCSV(Constants.sensorDataPath, 3).persist(StorageLevel.MEMORY_AND_DISK_SER_2)
    //    val sensorRDDHeader = SparkUtils.getHeader(sensorRDD)
    //    val sensorRDDWOHeader = SparkUtils.removeHeader(sensorRDD, sensorRDDHeader)
    //    val parsedSensorRDDWOHeader = SparkUtils.parseCSVData(sensorRDDWOHeader, new SensorLocation())
    //    SparkUtils.printPartitions(parsedSensorRDDWOHeader)
  }
  def loadDataframe()={
    val pedestrianCountDF = SparkUtils.readCSV(Schema.pedestrianCount, Constants.pedestrianDataPath, true)
    val sensorLocationDF = SparkUtils.readCSV(Schema.sensorLocation, Constants.sensorDataPath, true)

    val transformedPedestrianCountDF = pedestrianCountDF.withColumn("castedDateTime", f.to_timestamp(f.col("dateTime"), "MM/dd/yyyy hh:mm:ss a" ))
    transformedPedestrianCountDF.show(2, truncate = false)
    transformedPedestrianCountDF.printSchema()

    val transformedSensorLocationDF = sensorLocationDF
      .withColumn("castedInstallationDate", f.to_date(f.col("installationDate"), "yyyy/MM/dd"))
//      .withColumn("location", f.split(f.col("location" )))
  }

  def main(args: Array[String]): Unit = {
    loadDataframe()
    //    stopSparkSession()
  }
}
