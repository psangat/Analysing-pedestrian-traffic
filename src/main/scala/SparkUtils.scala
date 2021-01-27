import org.apache.spark.{RangePartitioner, TaskContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType

import scala.collection.mutable.Map
import scala.reflect.io.Path.extension

object SparkUtils extends SparkSessionWrapper  {
  /** Reading the CSV file using Spark.
   *
   *  @param schema schema of the CSV file
   *  @param filePath path of the CSV file
   *  @return a dataframe with a schema created using the CSV file
   */
  def readCSV(schema: StructType, filePath: String, header: Boolean = true): DataFrame ={
    val df = spark
      .read
      .option("header", header)
      .schema(schema)
      .csv(filePath)
    df
  }

  /** Reading the CSV file using Spark.
   *
   *  @param filePath path of the CSV file
   *  @param numberOfPartitions number of partitions to create
   *  @return a RDD of strings
   */
  def readCSV(filePath: String, numberOfPartitions: Integer): RDD[String] ={
    val rdd = spark
      .sparkContext
      .textFile(filePath, numberOfPartitions)
    rdd
  }

  def getHeader(rdd: RDD[String]): String = rdd.first()

  def removeHeader(rdd: RDD[String], header: String): RDD[String] = rdd.filter(line => line != header)

  private def parseLine(line: Array[String], classType: Any): Any ={
    if (classType.isInstanceOf[PedestrianCount])
    {
      val pedestrianCount = new PedestrianCount()
      pedestrianCount.id = line(0)
      pedestrianCount.dateTime = line(1)
      pedestrianCount.year = line(2)
      pedestrianCount.month= line(3)
      pedestrianCount.mDate = line(4)
      pedestrianCount.day = line(5)
      pedestrianCount.time = line(6)
      pedestrianCount.sensorID = line(7)
      pedestrianCount.sensorName = line(8)
      pedestrianCount.hourlyCounts = line(9)
      pedestrianCount
    }
    else{
      val sensorLocation = new SensorLocation()
      sensorLocation.sensorID = line(0)
      sensorLocation.sensorDescription = line(1)
      sensorLocation.sensorName = line(2)
      sensorLocation.installationDate = line(3)
      sensorLocation.status = line(4)
      sensorLocation.note = line(5)
      sensorLocation.direction1 = line(6)
      sensorLocation.direction2 = line(7)
      sensorLocation.latitude = line(8)
      sensorLocation.longitude = line (9)
      sensorLocation.location = (sensorLocation.latitude,sensorLocation.longitude)
      sensorLocation
    }
  }

  def parseCSVData(rdd:RDD[String], classType: Any): RDD[Any] ={
    rdd.map(line => line.split(",")).map(lineArray => parseLine(lineArray, classType))
  }

  def printPartitions(rdd:RDD[Any], numberOfRecordsToDisplay:Int = 2)={
    rdd.mapPartitionsWithIndex{ (index, iterator) => {
      println("Partition #" + index)
      iterator
    }}.take(numberOfRecordsToDisplay).foreach(println)
  }

  def countByPartition(rdd: RDD[(Int, Any)]) = {
    rdd.mapPartitions(iter => {
      val tc = TaskContext.get
      Iterator(("Partition #" + tc.partitionId(), "Item Count: " + iter.length))
    }).foreach(println)
  }
}
