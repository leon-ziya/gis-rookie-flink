package cn.edu.lnnu.gis.rookie.realtime.position.process

import java.util

import cn.edu.lnnu.gis.rookie.algorithm.util.dbscan.DbscanUtil
import cn.edu.lnnu.gis.rookie.base.common.entity.{ClassificationPoints, ClassificationPositions, Positions}
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.util.Collector

import scala.collection.mutable.ArrayBuffer

/**
 * @ClassName ClassificationPointsProcess.java
 * @author leon
 * @createTime 2020年10月27日 10:53:00
 */
class ClassificationPointsProcess extends ProcessFunction[Positions, ClassificationPositions]{

  override def processElement(
                               input: Positions,
                               context: ProcessFunction[Positions, ClassificationPositions]#Context,
                               collector: Collector[ClassificationPositions]): Unit = {

    val arrayBuffer = new ArrayBuffer[Vector[Double]]()
    //println("=====================================\n"+input.getVehiclePositions.size())
    for (i <- 0 to input.getVehiclePositions.size()-1) {

      arrayBuffer += Vector(input.getVehiclePositions.get(i).getLongitude, input.getVehiclePositions.get(i).getLatitude)
    }
    val arrayVector: Array[Vector[Double]] = arrayBuffer.toArray

    val dbscanUtil = new DbscanUtil
    val (cluster, types): (Array[Int], Array[Int]) = dbscanUtil.doDbscanAnalys(arrayVector, 200, 5)
    //println("分类数==》"+cluster.size)
    val classPoints = new util.ArrayList[ClassificationPoints]()
    for(i <- 0 to arrayVector.length -1){
      val classPoint = new ClassificationPoints
      val x: Double = arrayVector(i)(0)
      val y: Double = arrayVector(i)(1)
      classPoint.setLongitude(x)
      classPoint.setLatitude(y)
      //println("clusterValue==>"+cluster(i))
      classPoint.setCluster(cluster(i))
      classPoint.setTypes(types(i))
      classPoints.add(classPoint)
    }
    val classificationPositionsList = new ClassificationPositions
    classificationPositionsList.setClassificationPointsArrayList(classPoints)
    collector.collect(classificationPositionsList)
  }
}
