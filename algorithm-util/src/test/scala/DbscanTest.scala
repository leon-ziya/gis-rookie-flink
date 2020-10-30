
import java.text.SimpleDateFormat
import java.util.Date

import cn.edu.lnnu.gis.rookie.algorithm.util.dbscan.DbscanUtil
import cn.edu.lnnu.gis.rookie.base.common.entity.ClassificationPoints

import scala.collection.mutable.ArrayBuffer

/**
 * @ClassName DbscanTest.java
 * @author leon
 * @createTime 2020年10月19日 16:31:00
 */
object DbscanTest {

  def main(args: Array[String]): Unit = {

    val startTimeMillis: Long = System.currentTimeMillis()
    val format = new SimpleDateFormat("HH:mm:ss")
    val startTime: String = format.format(new Date(startTimeMillis))
    println("开始时间：===》"+startTime)

    val dbscanUtil = new DbscanUtil
    val points: Array[Vector[Double]] = dbscanUtil.readSource("/home/jianglai/space/AIRS/clusterPoint/points.csv", 2)

    println("数据集点坐标：")
    points.sortBy(v => v(0)).foreach(v => println(v))
    println("数据集点的总数： "+ points.size)

    val (cluster, types): (Array[Int], Array[Int]) = dbscanUtil.doDbscanAnalys(points, 2, 5)

    cluster.foreach(v => println("簇种类："+v))

    val stopTimeMillis: Long = System.currentTimeMillis()
    val stopTime: String = format.format(new Date(stopTimeMillis))
    println("开始时间：===》"+stopTime)

  }

}
