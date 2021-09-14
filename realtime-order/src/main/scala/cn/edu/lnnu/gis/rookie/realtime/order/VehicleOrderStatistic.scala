package cn.edu.lnnu.gis.rookie.realtime.order

import cn.edu.lnnu.gis.rookie.base.common.config.KafkaConfig
import cn.edu.lnnu.gis.rookie.base.common.entity.VehicleOrder
import cn.edu.lnnu.gis.rookie.base.common.enums.EmKafkaMessageTopic
import cn.edu.lnnu.gis.rookie.base.common.sink.MyPostgreSQLVehicleOrderSink
import com.google.gson.Gson
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.connector.jdbc.JdbcSink
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
 * @ClassName VehicleOrderStatistic.java
 * @author leon
 * @createTime 2021年02月09日 16:02:00
 */
object VehicleOrderStatistic {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val vehicleOrderDS: DataStream[VehicleOrder] = env.addSource(new FlinkKafkaConsumer(
      EmKafkaMessageTopic.KAFKA_SIMULATOR_VEHICLE_ORDER.getTpoic,
      new SimpleStringSchema(),
      KafkaConfig.getProperties
    )).map(row => new Gson().fromJson(row, classOf[VehicleOrder]))


    vehicleOrderDS.keyBy(_.getOrderId).addSink(new MyPostgreSQLVehicleOrderSink())
    vehicleOrderDS.print("====>")

    env.execute("VehicleOrderStatictic")
  }
}
