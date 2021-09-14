package cn.edu.lnnu.gis.rookie.vehicle.track

import cn.edu.lnnu.gis.rookie.base.common.config.KafkaConfig
import cn.edu.lnnu.gis.rookie.base.common.entity.VehicleOrder
import cn.edu.lnnu.gis.rookie.base.common.enums.EmKafkaMessageTopic
import com.google.gson.Gson
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
 * @ClassName VehicleTrackMonitor.java
 * @author leon
 * @createTime 2021年02月10日 18:06:00
 */
object VehicleTrackMonitor {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val value: DataStream[VehicleOrder] = env.addSource(new FlinkKafkaConsumer(
      EmKafkaMessageTopic.KAFKA_SIMULATOR_VEHICLE_ORDER.getTpoic,
      new SimpleStringSchema(),
      KafkaConfig.getProperties
    )).map(row => new Gson().fromJson(row, classOf[VehicleOrder]))


    env.execute("VehicleTrackMonitor")
  }

}
