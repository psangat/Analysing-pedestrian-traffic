import org.apache.spark.Partitioner
import scala.util.control.Breaks._
import scala.collection.mutable.ListBuffer

class PedestrianCountPartitioner(min: Int, max: Int, binSize: Int, var numParts: Int = 1) extends Partitioner {

  override def numPartitions: Int = numParts
  private def numPartitions_= (newNumPartitions: Int) = {numParts = newNumPartitions}

  private val rangeList = getRangeList(min, max, binSize)
  numPartitions = rangeList.length

  private def getRangeList(min:Int, max:Int, binSize:Int): List[List[Int]] ={
    val rangeList = new ListBuffer[List[Int]]()
    for(i <- min until max by binSize){
      val list = new ListBuffer[Int]()
      list += i
      list += i + binSize
      rangeList += list.toList
    }
     rangeList.toList
  }

  override def getPartition(key: Any): Int = {
    var index = 0
    breakable {
      for (range <- rangeList) {
        {
          if (range(0) until range(1) contains key) {
            break
          }
          index = index + 1
        }
      }
    }
    index
  }
}
