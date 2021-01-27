import org.apache.spark.sql.types.{DateType, DoubleType, IntegerType, LongType, StringType, StructType, TimestampType}

object Schema {
  // Schema for the flight data
    val pedestrianCount = new StructType()
      .add("id", LongType, false)
      .add("dateTime", StringType, false)
      .add("year", IntegerType, false)
      .add("month", StringType, false)
      .add("mDate", IntegerType, false)
      .add("day", StringType, false)
      .add("time", IntegerType, false)
      .add("sensorID", IntegerType, false)
      .add("sensorName", StringType, false)
      .add("hourlyCounts", IntegerType, false)

  // Schema for the passenger data
  val sensorLocation = new StructType()
    .add("sensorID", IntegerType, false)
    .add("sensorDescription", StringType, false)
    .add("sensorName", StringType, false)
    .add("installationDate", StringType, false)
    .add("status", StringType, false)
    .add("note", StringType, false)
    .add("direction1", StringType, false)
    .add("direction2", StringType, false)
    .add("latitude", DoubleType, false)
    .add("longitude", DoubleType, false)
    .add("location", StringType, false)
}
