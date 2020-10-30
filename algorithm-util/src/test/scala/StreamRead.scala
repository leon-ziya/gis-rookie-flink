
import cn.edu.lnnu.gis.rookie.algorithm.util.dbscan.DbscanUtil
import cn.edu.lnnu.gis.rookie.base.common.entity.ClassificationPoints
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import readTest.readSource

import scala.collection.mutable.ArrayBuffer

/**
 * @ClassName StreamRead.java
 * @author leon
 * @createTime 2020年10月27日 10:09:00
 */
object StreamRead {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)

    val sourceArr: Array[Vector[Double]] = readSource("/home/jianglai/space/AIRS/clusterPoint/points.csv", 2)
    val dbscanUtil = new DbscanUtil
    val (cluster, types): (Array[Int], Array[Int]) = dbscanUtil.doDbscanAnalys(sourceArr, 5, 5)
    val classPoints = new ArrayBuffer[ClassificationPoints]()
    for(i <- 0 to sourceArr.length -1){
      val classPoint = new ClassificationPoints
      val x: Double = sourceArr(i)(0)
      val y: Double = sourceArr(i)(1)
      classPoint.setLongitude(x)
      classPoint.setLatitude(y)
      classPoint.setCluster(cluster(i))
      classPoint.setTypes(types(i))
      classPoints.append(classPoint)
    }
    val classPointsArr: Array[ClassificationPoints] = classPoints.toArray
    val classificationPointsDS: DataStream[ClassificationPoints] = env.fromCollection(classPointsArr)

    classificationPointsDS.map(row => println(row))

    env.execute("StreamRead")
  }

}
