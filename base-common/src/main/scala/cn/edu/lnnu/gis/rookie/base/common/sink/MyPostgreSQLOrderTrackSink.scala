package cn.edu.lnnu.gis.rookie.base.common.sink

import java.sql.{Connection, DriverManager, PreparedStatement, Timestamp}
import java.util

import com.vividsolutions.jts.io.WKTReader
import com.vividsolutions.jts.geom.{Coordinate, GeometryFactory, LineString, MultiLineString, PrecisionModel}
import cn.edu.lnnu.gis.rookie.base.common.entity.{OrderTrack, Point2D, VehicleOrder}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.geolatte.geom.codec.Wkb
import org.postgresql.geometric.{PGline, PGpath, PGpoint}

/**
 * @ClassName MyPostgreSQLOrderTrackSink.java
 * @author leon
 * @createTime 2021年02月10日 21:52:00
 */
class MyPostgreSQLOrderTrackSink extends RichSinkFunction[OrderTrack]{

  var ps: PreparedStatement = null;
  var conn : Connection = null;
  var INSERT_CASE:String = "INSERT INTO traffic.\"order_track\"(\"orderId\", \"trackline\") values(?, ?);"

  override def open(parameters: Configuration)={
    // 加载驱动
    Class.forName("org.postgresql.Driver")
    // 数据库链接
    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gisrookie", "postgres", "jianglai")
    ps = conn.prepareStatement(INSERT_CASE)
  }

  override def invoke(value: OrderTrack)={
    try{
      // "ST_GeomFromText('LINESTRING(104.0422 30.2343, 104.2322 30.4211 )', 4326)"
      ps.setString(1, value.orderId)
      val trackList: util.ArrayList[Point2D] = value.track
      val trackListIter: util.Iterator[Point2D] = trackList.iterator()
//      var points : util.ArrayList[PGpoint] = new util.ArrayList[PGpoint]()
//      while (trackListIter.hasNext){
//        val point: Point2D = trackListIter.next()
//        val pGpoint = new PGpoint(point.x, point.y)
//        points.add(pGpoint)
//      }
//      val gpoints: Array[PGpoint] = points.toArray(new Array[PGpoint](points.size()))
//      val gpath = new PGpath(gpoints, true)
//      println("轨迹出来了===》"+gpath.toString+"===>"+gpoints.toString+"==>"+gpoints.size)

      val geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326)
      val lineStrings = new util.ArrayList[LineString]()
      for( i <-0 to trackList.size()-2){
        val point1: Point2D = trackList.get(i)
        val point2: Point2D = trackList.get(i + 1)
        val coordinate1 = new Coordinate(point1.x, point1.y)
        val coordinate2 = new Coordinate(point2.x, point2.y)
        val coordinateArr = new Array[Coordinate](2)
        coordinateArr(0) = coordinate1
        coordinateArr(1) = coordinate2
        val lineString: LineString = geometryFactory.createLineString(coordinateArr)
        lineStrings.add(lineString)
      }
      val lineStrArr: Array[LineString] = lineStrings.toArray(new Array[LineString](lineStrings.size()))
      val multiLineString: MultiLineString = geometryFactory.createMultiLineString(lineStrArr)
      ps.setObject(2, multiLineString)
      ps.execute()
    }catch{
      case a:Exception => print("报错了==》"+a.getMessage)
    }
  }
}
