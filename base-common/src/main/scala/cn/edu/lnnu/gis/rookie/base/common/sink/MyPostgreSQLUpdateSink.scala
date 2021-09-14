package cn.edu.lnnu.gis.rookie.base.common.sink

import java.io.InputStream
import java.sql.{Connection, DriverManager, JDBCType, PreparedStatement, Timestamp}
import java.util
import java.util.{ArrayList, Properties}

import cn.edu.lnnu.gis.rookie.base.common.entity.{OrderStartPointClassification, VehicleOrderPGis}
import cn.hutool.db.ds.DSFactory
import cn.hutool.db.ds.druid.DruidDSFactory
import com.vividsolutions.jts.geom.{Coordinate, GeometryFactory, Point, PrecisionModel}
import javax.sql.DataSource
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction

/**
 * @ClassName MyPostgreSQLUpdate.java
 * @author leon
 * @createTime 2021年02月26日 12:46:00
 */
class MyPostgreSQLUpdateSink extends RichSinkFunction[ArrayList[VehicleOrderPGis]]{

  var ps: PreparedStatement = null;
  var conn : Connection = null;
  var INSERT_CASE:String = " update traffic.vehicle_order_csv " +
    "set end_time = ? where order_id = ? AND end_time is null ;"

  override def open(parameters: Configuration)={
    // 加载驱动
    Class.forName("org.postgresql.Driver")
    // 数据库链接
    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gisrookie", "postgres", "jianglai")
    ps = conn.prepareStatement(INSERT_CASE)
  }

  override def invoke(value: util.ArrayList[VehicleOrderPGis])={
    try{
      println("存储数据到Postgresql===》"+value.size())
      val geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326)
      for(i <- 0 to value.size()-1){
        val vehicleOrderPGis: VehicleOrderPGis = value.get(i)
        println("存储订单数据：===》  "+ vehicleOrderPGis.getOrderId)
        println("sql == > "+ ps.toString)
        ps.setObject(1, new Timestamp(vehicleOrderPGis.getEndTime.getTime))
        ps.setString(2, vehicleOrderPGis.getOrderId)
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
