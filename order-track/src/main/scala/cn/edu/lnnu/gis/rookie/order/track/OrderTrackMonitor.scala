package cn.edu.lnnu.gis.rookie.order.track

import cn.edu.lnnu.gis.rookie.base.common.config.KafkaConfig
import cn.edu.lnnu.gis.rookie.base.common.entity.{OrderTrack, VehicleOrder, VehiclePosition}
import cn.edu.lnnu.gis.rookie.base.common.enums.EmKafkaMessageTopic
import cn.edu.lnnu.gis.rookie.base.common.sink.MyPostgreSQLOrderTrackSink
import cn.edu.lnnu.gis.rookie.order.track.process.OrderAndPositionConnectProcess
import com.google.gson.Gson
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
 * @ClassName OrderTrackMonitor.java
 * @author leon
 * @createTime 2021年02月10日 19:45:00
 */
object OrderTrackMonitor {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    /**
     * 从kafka获取订单数据
     */
    val orderDs: DataStream[VehicleOrder] = env.addSource(new FlinkKafkaConsumer(
      EmKafkaMessageTopic.KAFKA_SIMULATOR_VEHICLE_ORDER.getTpoic,
      new SimpleStringSchema(),
      KafkaConfig.getProperties
    )).map(row => new Gson().fromJson(row, classOf[VehicleOrder])).keyBy(_.getOrderId)

    /**
     * 从kafka中获取车辆实时位置数据
     */
    val positionDS: DataStream[VehiclePosition] = env.addSource(new FlinkKafkaConsumer(
      EmKafkaMessageTopic.KAFKA_SIMULATOR_VEHICLE_POSITION.getTpoic,
      new SimpleStringSchema(),
      KafkaConfig.getProperties
    )).map(row => new Gson().fromJson(row, classOf[VehiclePosition])).keyBy(_.getOrderId)


    /**
     * 双流connect计算订单的轨迹
     */
    val orderTrackDs: DataStream[OrderTrack] = positionDS.connect(orderDs).process(new OrderAndPositionConnectProcess())

    orderTrackDs.print("订单轨迹====》")

    /**
     * 订单轨迹轨迹数据输出
     */
    orderTrackDs.addSink(new MyPostgreSQLOrderTrackSink())  // 订单轨迹数据存储到PostgreSQL

    env.execute("OrderTrackMonitor")
  }

}
