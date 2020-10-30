package cn.edu.gis.rookie.algorithm.util.dbscan.fuction

import java.util

import org.osgeo.proj4j.{CRSFactory, CoordinateReferenceSystem, CoordinateTransform, CoordinateTransformFactory, ProjCoordinate}


/**
 * @author leon
 */
object CoordinateTransform {
  val wgs84Param = "+title=long/lat:WGS84 +proj=longlat +datum=WGS84 +units=degrees"
  private val tgtCRS = "+proj=tmerc +lat_0=0 +lon_0=117 +y_0=0 +x_0=500000 +k=0.9996 +zone=50 +to_meter=1 +a=6378137 +ellps=WGS84 +units=m +no_defs"
  private val ctFactory = new CoordinateTransformFactory
  private val crsFactory = new CRSFactory
  private val wgs84: CoordinateReferenceSystem = crsFactory.createFromParameters("WGS84", wgs84Param)


  /**
   * 创建坐标参考系
   * @param crsSpec
   * @return
   */
  def createCRS(crsSpec: String): CoordinateReferenceSystem = {
    var crs: CoordinateReferenceSystem = null
    if(crsSpec.indexOf("+") >= 0 || crsSpec.indexOf("=") >= 0){
      crs = crsFactory.createFromParameters("Anon", crsSpec)
      crs
    }else{
      crs= crsFactory.createFromName(crsSpec)
      crs
    }
  }

  /**
   * 坐标转换 : wgs84 => tgtCRS
   *
   * @param x
   * @param y
   */
  def getCoordinateWGS84ToUTM(x: Double, y: Double) = {
    val result = new util.LinkedList[Double]

    val transform: CoordinateTransform = ctFactory.createTransform(createCRS(wgs84Param), createCRS(tgtCRS))
    val pout = new ProjCoordinate()
    val p = new ProjCoordinate(x, y)
    transform.transform(p, pout)
    result.add(pout.x)
    result.add(pout.y)
    result
  }

  /**
   * 坐标转换： tgtCRS => wgs84
   * @param x
   * @param y
   * @return
   */
  def getCoordinateUTMToWGS84(x: Double, y: Double) = {
    val result = new util.LinkedList[Double]

    val transform: CoordinateTransform = ctFactory.createTransform(createCRS(tgtCRS), createCRS(wgs84Param))
    val pout = new ProjCoordinate()
    val p = new ProjCoordinate(x, y)
    transform.transform(p, pout)
    result.add(pout.x)
    result.add(pout.y)
    result
  }
}
