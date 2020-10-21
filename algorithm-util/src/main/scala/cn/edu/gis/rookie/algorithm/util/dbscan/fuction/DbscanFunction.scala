package cn.edu.gis.rookie.algorithm.util.dbscan.fuction

import cn.edu.lnnu.gis.rookie.base.common.entity.Point2D

/**
 * @ClassName DbscanFunction.java
 * @author leon
 * @createTime 2020年10月19日 17:47:00
 */
class DbscanFunction() {


  def vectorDistance(vector: Vector[Double], xTempPoint: Vector[Double]): Unit ={
    val startPoint = Point2D(xTempPoint(0), xTempPoint(1))
    val stopPoint = Point2D(vector(0), vector(1))
    // 将角度转化为弧度
    val radLng1: Double = radians(startPoint.x)
    val radLat1: Double = radians(startPoint.y)
    val radLng2: Double = radians(stopPoint.x)
    val radLat2: Double = radians(stopPoint.y)

    val distance: Double = 2 * Math.asin(Math.sqrt(
      Math.sin((radLat1 - radLat2) / 2) * Math.sin((radLat1 - radLat2) / 2) +
        Math.cos(radLat1) * Math.cos(radLat2) *
          Math.sin((radLng1 - radLng2) / 2) * Math.sin((radLng1 - radLng2) / 2)
    )) * 6378.137
    distance
  }

  /**
   * 将角度转化为弧度
   * @param d
   * @return
   */
  def radians(d: Double):Double={
    d * Math.PI/180.0
  }
}
