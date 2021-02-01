package com.pscodes.trafficprediction.common.feeds.dtclassifiermodel

import com.pscodes.trafficprediction.common.feeds.Feed
import com.pscodes.trafficprediction.common.feeds.io.FeedIO
import org.apache.spark.ml.classification.DecisionTreeClassifier

object DTClassifierModelFeed {
  /**
   * @return a feed for DT Classfier as an DTModel instance.
   */
  def apply(): Feed[DecisionTreeClassifier] = Feed(DTClassifierModelFeedIO(), "training/dt/v1.0/")

}

class DTClassifierModelFeedIO extends FeedIO[DecisionTreeClassifier]{
  override def read(path: String): DecisionTreeClassifier = DecisionTreeClassifier.load(path)

  override def write(update: DecisionTreeClassifier, path: String): Unit = {
    update.write.save(path)
  }
}

object DTClassifierModelFeedIO {
  def apply(): DTClassifierModelFeedIO = new DTClassifierModelFeedIO
}
