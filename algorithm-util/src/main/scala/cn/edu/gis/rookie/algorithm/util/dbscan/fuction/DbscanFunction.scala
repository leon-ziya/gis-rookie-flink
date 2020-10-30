package cn.edu.gis.rookie.algorithm.util.dbscan.fuction

import cn.edu.lnnu.gis.rookie.base.common.entity.Point2D

/**
 * @ClassName DbscanFunction.java
 * @author leon
 * @createTime 2020年10月19日 17:47:00
 */
class DbscanFunction() {


  /**
   * 计算两点间距离
   * @param vector
   * @param xTempPoint
   * @return
   */
  def vectorDistance(vector: Vector[Double], xTempPoint: Vector[Double]) ={
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
    )) * 6378137
    Math.round(distance*10000)/10000.toDouble
  }


  /**
   * 将角度转化为弧度
   * @param d
   * @return
   */
  def radians(d: Double):Double={
    d * Math.PI/180.0
  }

  def getAround(lon: Double, lat:Double, radius: Double )={
    val degree = (24901*1609)/360.0
    val dpmLat: Double = 1 / degree
    val radiusLat: Double = dpmLat * radius
    val minLat: Double = lat - radiusLat
    val maxLat: Double = lat + radiusLat

    val mpdLng: Double = degree * Math.cos(lat * (Math.PI / 180))
    val dpmLng: Double = 1 / mpdLng
    val radiusLng: Double = dpmLng * radius
    val minLng: Double = lon - radiusLng
    val maxLng: Double = lon + radiusLng

    (minLat, maxLat, minLng, maxLng)
  }


  /**
   * 结果打印
   * @param data
   * @param cluster
   * @param types
   */
  def printResult(data:Array[Vector[Double]],cluster:Array[Int],types:Array[Int]) = {
    val result = data.map(v => (cluster(data.indexOf(v)),v)).groupBy(v => v._1) //Map[int,Array[(int,Vector[Double])]]
    //key代表簇号，value代表属于这一簇的元素数组
    result.foreach(v =>{
      println("簇" + v._1 + "包含的元素如下:")
      v._2.foreach(v => println(v._2))
      println("簇" + v._1 + "包含元素： "+ v._2.size)
    })
    //val noise = cluster.zip(data).filter(v => v._1 ==0)
    //noise.foreach(v => types(data.indexOf(v._2)) = -1) //通过簇号0把噪音点在types中赋值-1,数据集中没有包含在任何簇中(即簇号为0)的数据点就构成异常点
    val pointsTypes = data.map(v => (types(data.indexOf(v)),v)).groupBy(v => v._1) //Map[点类型int,Array[(点类型int,Vector[Double])]]
    //key代表点的类型号，value代表属于这一类型的元素数组
    pointsTypes.foreach(v =>{
      if(v._1 == 1) println("核心点如下：")
      else if(v._1 ==0) println("边界点如下：")
      else println("噪音点如下：")
      v._2.foreach(v => println(v._2))
    })
    val resultMat = cluster.zip(types).zip(data) //Array[((Int,Int),Vector[Double])],即Array[((簇Id，类型Id),点向量)]
    val resultMat1 = resultMat.groupBy(v => v._1) //Map[(Int,Int),Array[((Int,Int),Vector[Double])]]
    resultMat1.foreach(v => {
      val arr = v._2
      if(v._1._2 == 1){
        println("簇"+v._1._1+"的核心点集的x坐标为：")
        arr.foreach(v => print(v._2(0)+","))
        println()
        println("簇"+v._1._1+"的核心点集的y坐标为：")
        arr.foreach(v => print(v._2(1)+","))
        println()
      }else if(v._1._2 == 0){
        println("簇"+v._1._1+"的边界点集的x坐标为：")
        arr.foreach(v => print(v._2(0)+","))
        println()
        println("簇"+v._1._1+"的边界点集的y坐标为：")
        arr.foreach(v => print(v._2(1)+","))
        println()
      }else{
        println("噪音点集的x坐标为：")
        arr.foreach(v => print(v._2(0)+","))
        println()
        println("噪音点集的y坐标为：")
        arr.foreach(v => print(v._2(1)+","))
        println()
      }
    })
  }
}
