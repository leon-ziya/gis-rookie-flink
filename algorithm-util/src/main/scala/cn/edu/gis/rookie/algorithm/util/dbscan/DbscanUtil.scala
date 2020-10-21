package cn.edu.gis.rookie.algorithm.util.dbscan

import cn.edu.gis.rookie.algorithm.util.dbscan.fuction.DbscanFunction

import scala.io.Source

/**
 *  DBSCAN 算法工具
 *
 * @ClassName DbscanUtil.java
 * @author leon
 * @createTime 2020年10月19日 15:43:00
 */
class DbscanUtil {
  private val dbscanFunction = new DbscanFunction

  /**
   * 读取数据
   * @param file
   * @param dim
   * @return
   */
  def readSource(file: String, dim: Int): Array[Vector[Double]] ={
    val lines: Iterator[String] = Source.fromFile(file).getLines()
    val points: Array[Vector[Double]] = lines.map(row => {
      val ports: Array[Double] = row.drop(1).dropRight(1).split(",").map(_.toDouble)
      var vector: Vector[Double] = Vector[Double]()
      for (i <- 0 to dim - 1) {
        vector ++= Vector(ports(i))
      }
      vector
    }).toArray
    points
  }

  def doDbscanAnalys(points:Array[Vector[Double]], e: Double, minPts: Int): Unit ={
    // 给数据添加两个标签
    val types: Array[Int] = (for (i <- 0 to points.length - 1) yield -1).toArray //用于区分核心点=1, 边界点=0, 噪声点=-1,
    val visited: Array[Int] = (for (i <- 0 to points.length - 1) yield 0).toArray // 用于判断该点是否处理过, 0未处理过
    var number = 1 // 用于标记
    var xTempPoint: Vector[Double] = Vector(0.0,0.0)

    val distance = new Array[(Double, Int)](1)

    for(i <- 0 to points.length - 1){
      if(visited(i) == 0){
        visited(i) == 1
        xTempPoint= points(i)
        distance = points.map(point => (dbscanFunction.vectorDistance(point, xTempPoint), points.indexOf(point)))
      }
    }
  }
}
