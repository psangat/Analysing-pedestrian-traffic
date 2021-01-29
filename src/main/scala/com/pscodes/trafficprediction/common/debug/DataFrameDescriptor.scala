package com.pscodes.trafficprediction.common.debug

import org.apache.spark.sql.DataFrame
import com.pscodes.trafficprediction.common.config.Configuration

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
  override def describe(df: DataFrame): Unit = {
    df.show(truncate = false)
    df.describe().show(truncate = false)
  }
}
