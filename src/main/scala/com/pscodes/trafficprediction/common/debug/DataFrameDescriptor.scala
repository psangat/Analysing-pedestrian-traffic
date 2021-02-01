package com.pscodes.trafficprediction.common.debug

import org.apache.spark.sql.DataFrame
import com.pscodes.trafficprediction.common.config.Configuration
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{functions => f}

import scala.collection.mutable.ListBuffer

trait DataFrameDescriptor {

  /**
   * Describes the given dataframe.
   * @param df the dataframe to be described.
   */
  def describe(df: DataFrame): Unit
}

object DataFrameDescriptor {

  def apply(): DataFrameDescriptor = {
    if(Configuration().DebugEnabled)
    {
      new DebugDataFrameDescriptor
    }
    else{
      new NoOpDataFrameDescriptor
    }
  }
}

class NoOpDataFrameDescriptor extends DataFrameDescriptor{
  override def describe(df: DataFrame): Unit = {}
}

class DebugDataFrameDescriptor extends DataFrameDescriptor{

  private def getNonNumericColumnProfile(df: DataFrame, columnName: String): DataFrame = {
    df.select(columnName)
      .withColumn("isEmpty", f.when(f.col(columnName) === "", true).otherwise(null))
      .withColumn("isNull", f.when(f.col(columnName).isNull, true).otherwise(null))
      .withColumn("fieldLen", f.length(f.col(columnName)))
      .agg(
        f.max(f.col("fieldLen")).as("max_length"),
        f.countDistinct(columnName).as("unique"),
        f.count("isEmpty").as("is_empty"),
        f.count("isNull").as("is_null")
      )
      .withColumn("col_name", f.lit(columnName))
  }

  override def describe(df: DataFrame): Unit = {
    val numericColumns = new ListBuffer[String]()
    val nonNumericColumns = new ListBuffer[String]()
    df.dtypes.foreach(record =>
      if (record._2.equals("LongType") || record._2.equals("IntegerType") || record._2.equals("DoubleType"))
      {
        numericColumns += record._1
      }
      else if (record._2.equals("StringType")) {
        nonNumericColumns += record._1
      })
    df.show(truncate = false)
    df.select(numericColumns.map(f.col): _*).summary().show(truncate = false)

    nonNumericColumns.map(getNonNumericColumnProfile(df, _))
      .reduce(_ union _)
      .toDF
      .select("col_name"
        , "unique"
        , "is_empty"
        , "is_null"
        , "max_length")
      .show(truncate = false)

  }
}
