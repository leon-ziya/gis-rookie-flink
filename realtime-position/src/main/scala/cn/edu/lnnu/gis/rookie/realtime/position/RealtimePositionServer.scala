package cn.edu.lnnu.gis.rookie.realtime.position

import java.util.ArrayList

import cn.edu.lnnu.gis.rookie.base.common.config.KafkaConfig
import cn.edu.lnnu.gis.rookie.base.common.constant.Constant
import cn.edu.lnnu.gis.rookie.base.common.entity.{Order, Positions, VehiclePosition}
import cn.edu.lnnu.gis.rookie.base.common.enums.EmKafkaMessageTopic
import cn.edu.lnnu.gis.rookie.base.common.sink.HttpAsyncSink
import cn.edu.lnnu.gis.rookie.realtime.position.process.ReduceProcessFuction
import com.google.gson.Gson
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.{SlidingAlignedProcessingTimeWindows, TumblingAlignedProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer


/**
 * @ClassName RealtimePositionServer.java
 * @author leon
 * @createTime 2020年10月16日 09:16:00
 */
object RealtimePositionServer {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)


    val vehiclePositionDS: DataStream[VehiclePosition] = env.addSource(new FlinkKafkaConsumer(
      EmKafkaMessageTopic.KAFKA_SIMULATOR_VEHICLE_POSITION.getTpoic,
      new SimpleStringSchema(),
      KafkaConfig.getProperties
    )).map(row => new Gson().fromJson(row, classOf[VehiclePosition]))


    val positionsDS: DataStream[Positions] = vehiclePositionDS
      .keyBy(row => row.getDriverId)
      .timeWindowAll(Time.seconds(30), Time.seconds(2))
      .process(new ReduceProcessFuction())

    /**
     * 将数据通过webSocket发送
     */
    positionsDS.addSink(new HttpAsyncSink[Positions](Constant.WEBSOCKET_DASHBOARD_URL_VEHICLE_POSITION))

    env.execute("RealTimePositionServic")
  }

}
