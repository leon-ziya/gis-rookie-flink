import cn.edu.lnnu.gis.rookie.algorithm.util.dbscan.DbscanUtil

import scala.collection.mutable.ArrayBuffer

/**
 * @ClassName test.java
 * @author leon
 * @createTime 2020年10月29日 19:41:00
 */
object test {
  def main(args: Array[String]): Unit = {
    val dbscanUtil = new DbscanUtil
    val points: Array[Vector[Double]] = dbscanUtil.readSource("/home/jianglai/space/AIRS/clusterPoint/point3.csv", 2)
    val filterY = new Array[Vector[Double]](1)
    points.sortBy(v => v(0)).foreach(v => println("points==>" + v))
    points.sortBy(v => v(0)).toTraversable.slice(points.length - 1, points.length).copyToArray(filterY)
    //println("查找==》"+filterY.find(v=>v(0)==xTempPoint(0) && v(1) == xTempPoint(1)).get)
    filterY.foreach(v => println("filterY==>" + v))

  }
}
