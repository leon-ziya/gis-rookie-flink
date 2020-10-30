import java.text.{DateFormat, SimpleDateFormat}
import java.util
import java.util.Date

import cn.edu.gis.rookie.algorithm.util.dbscan.fuction.CoordinateTransform
import cn.edu.lnnu.gis.rookie.algorithm.util.dbscan.DbscanUtil
import cn.edu.lnnu.gis.rookie.base.common.entity.ClassificationPoints

import scala.io.Source
import cn.edu.lnnu.gis.rookie.base.common.constant.Constant
import cn.edu.lnnu.gis.rookie.base.common.sink.HttpAsyncSink

import scala.collection.mutable.ArrayBuffer
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
 * @ClassName readTest.java
 * @author leon
 * @createTime 2020年10月26日 17:17:00
 */
object readTest {
  def main(args: Array[String]): Unit = {
    // 开始时间
    val currentTimeMillis: Long = System.currentTimeMillis
    val sdff = new SimpleDateFormat("HH:mm:ss")
    val systemTime: String = sdff.format(new Date(currentTimeMillis))
    println("开始时间：========》"+systemTime)
    import org.apache.flink.api.scala._

    // 读取数据
    val sourceArr: Array[Vector[Double]] = readSource("/home/jianglai/space/AIRS/clusterPoint/clusterPoint_train.csv", 2)
    val wgs84Arr: Array[Vector[Double]] = sourceArr.map(v => {
      val result: util.LinkedList[Double] = CoordinateTransform.getCoordinateUTMToWGS84(v(0), v(1))
      Vector(result.get(0), result.get(1))
    })
    println("数据量==》"+wgs84Arr.length)

    // 聚类分析
    val dbscanUtil = new DbscanUtil
    val (cluster, types): (Array[Int], Array[Int]) = dbscanUtil.doDbscanAnalys(wgs84Arr, 2, 5)

    // 结果输出
    cluster.foreach(v => println("簇种类："+v))

    // 结束时间
    val stopTime: Long = System.currentTimeMillis()
    val sysStopTime: String = sdff.format(new Date(stopTime))
    println("结束时间：====》"+sysStopTime)

  }

  def readSource(file: String, dim: Int)={

    val lines: Iterator[String] = Source.fromFile(file).getLines()
    val sourceArr = new ArrayBuffer[Vector[Double]]()
    lines.drop(1).foreach(v => {
      val strArr: Array[String] = v.split(",")
      val x: Double = strArr(1).toDouble
      val y: Double = strArr(2).toDouble
//      val result: util.LinkedList[Double] = CoordinateTransform.getCoordinateUTMToWGS84(x, y)
//      sourceArr += Vector(result.get(0), result.get(1))
      sourceArr += Vector(x, y)
    })
    sourceArr.toArray[Vector[Double]]
  }

}
