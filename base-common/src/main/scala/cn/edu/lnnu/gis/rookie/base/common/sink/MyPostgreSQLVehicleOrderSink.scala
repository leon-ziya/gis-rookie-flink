package cn.edu.lnnu.gis.rookie.base.common.sink

import java.sql.{Connection, DriverManager, PreparedStatement, Timestamp}

import cn.edu.lnnu.gis.rookie.base.common.DateAndTimeUtils
import cn.edu.lnnu.gis.rookie.base.common.entity.VehicleOrder
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.slf4j.LoggerFactory

/**
 * @ClassName MyPostgreSQLSink.java
 * @author leon
 * @createTime 2021年02月09日 17:09:00
 */
class MyPostgreSQLVehicleOrderSink extends RichSinkFunction[VehicleOrder] {

  var ps: PreparedStatement = null;
  var conn : Connection = null;
  var INSERT_CASE:String = "INSERT INTO traffic.\"vehicleOrder\"(\"orderId\", \"startTime\", \"endTime\", earn, mileage, status) values(?, ?, ?, ?, ?, ?);"
  private val dateAndTimeUtils = new DateAndTimeUtils
  override def open(parameters: Configuration)={
    // 加载驱动
    Class.forName("org.postgresql.Driver")
    // 数据库链接
    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gisrookie", "postgres", "jianglai")
    ps = conn.prepareStatement(INSERT_CASE)
  }

  override def invoke(value: VehicleOrder)={
    try{
      var flag = -1;
      if(value.isOrderStart)
        flag = 0
      if(value.isOrderEnd)
        flag = 1
      val startTime = new Timestamp(value.getStartTimeStamp.toLong * 1000)
      val endTime = new Timestamp(value.getEndTimeStamp.toLong * 1000)
      // to_timestamp('2049-11-22 21:21:21.4','YYYY-MM-DD HH24:MI:SS.MS')
      ps.setString(1, value.getOrderId)
      ps.setObject(2, startTime)
      ps.setObject(3, endTime)
      ps.setInt(4, 0)
      ps.setInt(5, 0)
      ps.setInt(6, flag)
      ps.execute()
    }catch{
      case a:Exception => print("报错了==》"+a.getMessage)
    }
  }
}
