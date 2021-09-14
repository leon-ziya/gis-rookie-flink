package cn.edu.lnnu.gis.rookie.base.common.sink

import java.sql.{Connection, DriverManager, JDBCType, PreparedStatement}
import java.util

import cn.edu.lnnu.gis.rookie.base.common.entity.{OrderStartPointClassification, OrderTrack, Point2D}
import com.vividsolutions.jts.geom.{Coordinate, GeometryFactory, LineString, MultiLineString, Point, PrecisionModel}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction

/**
 * @ClassName MyPostgresqlOrderStartPointClassification.java
 * @author leon
 * @createTime 2021年02月20日 18:09:00
 */
class MyPostgresqlOrderStartPointSink extends RichSinkFunction[util.ArrayList[OrderStartPointClassification]]{

  var ps: PreparedStatement = null;
  var conn : Connection = null;
  var INSERT_CASE:String = "INSERT INTO traffic.\"start_point\"(\"orderId\", \"longitude\", \"latitude\", \"position\", \"cluster\", \"type\") values(?, ?, ?, ?, ?, ?);"

  override def open(parameters: Configuration)={
    // 加载驱动
    Class.forName("org.postgresql.Driver")
    // 数据库链接
    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gisrookie", "postgres", "jianglai")
    ps = conn.prepareStatement(INSERT_CASE)
  }

  override def invoke(value: util.ArrayList[OrderStartPointClassification])={
    try{
      println("存储数据到Postgresql===》"+value.size())
      val geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326)
      for(i <- 0 to value.size()-1){
        val orderStartPoint: OrderStartPointClassification = value.get(i)
        val coordinatePoint = new Coordinate(orderStartPoint.getLongitude, orderStartPoint.getLatitude)
        val position: Point = geometryFactory.createPoint(coordinatePoint)
        ps.setString(1, orderStartPoint.getOrderId)
        ps.setDouble(2, orderStartPoint.getLongitude)
        ps.setDouble(3, orderStartPoint.getLatitude)
        ps.setObject(4, position, JDBCType.JAVA_OBJECT)
        ps.setInt(5, orderStartPoint.getCluster)
        ps.setInt(6, orderStartPoint.getTypes)
        ps.addBatch()
      }
      ps.executeBatch()
    }catch{
      case a:Exception => print("报错了==》"+a.getMessage)
    }finally {
      ps.close()
    }
  }
}
