package cn.edu.lnnu.gis.rookie.algorithm.util.dbscan

import cn.edu.gis.rookie.algorithm.util.dbscan.fuction.DbscanFunction
import scala.util.control.Breaks._
import scala.collection.mutable.ArrayBuffer
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

  def getNeighPoints(xTempPoint: Vector[Double], points:Array[Vector[Double]], r: Double): Array[Vector[Double]] = {
    var distance = new Array[(Double, Int)](1)
    val filterX = new Array[Vector[Double]](points.length)
    var xIndexStart: Int = -1;
    var xIndexStop: Int = -1;
    var yIndexStart: Int = -1;
    var yIndexStop: Int = -1;
    // 获取该点到其他所有点的距离Array[(distance, index]
    val tuple1: (Double, Double, Double, Double) = dbscanFunction.getAround(xTempPoint(0), xTempPoint(1), r)
    points.sortBy(v => v(0)).copyToArray(filterX)
    val xTempIndex: Int = filterX.indexOf(filterX.find(v => v(0) == xTempPoint(0) && v(1) == xTempPoint(1)).get)
    // x -- start
    if(tuple1._3 < filterX.head(0)){
      xIndexStart = 0
    }else{
      breakable{
        for(i <- (0 to xTempIndex).reverse){
          if(filterX(i)(0) < tuple1._3){
            xIndexStart = i+1
            break
          }
        }
      }
    }

    // x -- stop
    if(tuple1._4 > filterX.last(0)){
      xIndexStop = filterX.length
    }else{
      breakable{
        for(i <- xTempIndex to filterX.length-1){
          if(filterX(i)(0) > tuple1._4){
            xIndexStop = i
            break
          }else if(xTempIndex.equals(filterX.length-1)){
            xIndexStop = i +1
            break
          }
        }
      }
    }

    // 根据x范围截取filterX
    val filterXCut: Array[Vector[Double]] = filterX.toTraversable.slice(xIndexStart, xIndexStop).toArray
    val filterY = new Array[Vector[Double]](filterXCut.length)
    filterXCut.copyToArray(filterY)
    val yTempIndex: Int = filterY.indexOf(filterY.find(v => v(0) == xTempPoint(0) && v(1) == xTempPoint(1)).get)
    // y --- start
    if(tuple1._1 < filterY.head(1)){
      yIndexStart = 0
    }else{
      breakable{
        for(i <- (0 to yTempIndex).reverse){
          if(filterY(i)(1) < tuple1._1){
            yIndexStart = i+1
            break
          }
        }
      }
    }

    // y --- stop
    if(tuple1._2 > filterY.last(1)){
      yIndexStop = filterY.length
    }else{
      breakable{
        for(i <-  yTempIndex to filterY.length -1 ){
          if(filterY(i)(1) > tuple1._2){
            yIndexStop = i
            break
          }else if(yTempIndex.equals(filterY.length-1)){
            yIndexStop = i+1
            break
          }
        }
      }
    }

    // filterY 根据y的范围截取
    val filterYCut: Array[Vector[Double]] = filterY.toTraversable.slice(yIndexStart, yIndexStop).toArray
//    distance = filterYCut.map(point => (dbscanFunction.vectorDistance(point, xTempPoint), points.indexOf(point)))
//    // 找出半径r内的所有点
//    distance.filter(x =>x._1 <= r).map(v => points(v._2))
    filterYCut
  }

  def doDbscanAnalys(points:Array[Vector[Double]], r: Double, minPts: Int) ={
    // 给数据添加两个标签
    val types: Array[Int] = (for (i <- 0 to points.length - 1) yield -1).toArray //用于区分核心点=1, 边界点=0, 噪声点=-1,
    val visited: Array[Int] = (for (i <- 0 to points.length - 1) yield 0).toArray // 用于判断该点是否处理过, 0未处理过
    var number: Int = 1 // 用于标记
    var xTempPoint: Vector[Double] = Vector(0.0,0.0)
    var yTempPoint: Vector[Double] = Vector(0.0,0.0)
    var distance = new Array[(Double, Int)](1)
    var distanceTemp = new Array[(Double, Int)](1)
    val neighPoints = new ArrayBuffer[Vector[Double]]()
    val neighPointsTemp = new ArrayBuffer[Vector[Double]]()
    var index = 0
    // 用于标记每个数据点所属类别
    val cluster = new Array[Int](points.length)
    for(i <- 0 to points.length-1){
      //cluster.foreach(v => println("cluster====>"+v))
      if(visited(i) == 0){
        visited(i) == 1
        xTempPoint= points(i)

        // 计算该点的邻域点集
        neighPoints ++= getNeighPoints(xTempPoint,points, r)


        // TODO 非核心点
        // 若其邻域内有核心点，则该点为边界点
        if(neighPoints.length >1 && neighPoints.length < minPts){
          breakable{
            for(j <- 0 to neighPoints.length -1){
              var index: Int = points.indexOf(neighPoints(j))
              if(types(index) == 1){
                types(i) = 0; // 边界点
                break
              }
            }
          }
        }

        // TODO 核心点
        // 此时neightPoints表示以该核心点出发的密度相连点的集合
        if(neighPoints.length >= minPts){
          types(i) = 1
          cluster(i) == number

          // 对该核心点邻域内的点迭代寻找核心点，直到所有核心点邻域半径内的点组成的集合不再扩大（每次聚类）
          while(neighPoints.isEmpty == false){
            // 取集合中第一个点
            yTempPoint = neighPoints.head
            index = points.indexOf(yTempPoint)
            // 若该点未被处理， 则标记已处理
            if(visited(index) == 0){
              visited(index) == 1

              // 划分到与核心点一样的簇中
              if(cluster(index) == 0){
                cluster(index) = number
              }
              // 取得该点到其他所有点的距离Array[(distance, index]
              // 计算该点的邻域点集
              neighPointsTemp ++= getNeighPoints(yTempPoint,points, r)

              // TODO 核心点
              if(neighPointsTemp.length >= minPts){
                // 该点为核心点
                types(index) = 1
                // 将其邻域内未分类的对象划分到簇中，然后放进neighPoints
                for(k<- 0 to neighPointsTemp.length -1){
                  if(cluster(points.indexOf(neighPointsTemp(k))) == 0){
                    // 只划分簇，没有访问到
                    cluster(points.indexOf(neighPointsTemp(k))) = number
                    neighPoints += neighPointsTemp(k)
                  }
                }
              }

              // TODO 非核心点
              if(neighPointsTemp.length > 1 && neighPointsTemp.length < minPts){
                breakable{
                 // 此为非核心点，若其邻域内有核心点，则该点为边界点
                  for(l <- 0 to neighPointsTemp.length -1){
                    var index1: Int = points.indexOf(neighPointsTemp(l))
                    if(types(index1) == 1){
                      types(index) = 0 // 边界点
                      break
                    }
                  }
                }
              }
            }
            neighPoints -= yTempPoint // 剔除该点
          } // end-while
          // 进行新的聚类
          number = number + 1
        }
      }
    }
    (cluster, types)
  }

  /**
   * 打印结果
   * @param data
   * @param cluster
   * @param types
   */
  def  printResult(data:Array[Vector[Double]],cluster:Array[Int],types:Array[Int]): Unit ={
    dbscanFunction.printResult(data, cluster, types)
  }

}
