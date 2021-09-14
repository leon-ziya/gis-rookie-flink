package cn.edu.lnnu.gis.rookie.realtime.position

import java.util.ArrayList

import cn.edu.lnnu.gis.rookie.base.common.config.KafkaConfig
import cn.edu.lnnu.gis.rookie.base.common.constant.Constant
import cn.edu.lnnu.gis.rookie.base.common.entity.{ClassificationPoints, Positions, VehicleOrder, VehiclePosition}
import cn.edu.lnnu.gis.rookie.base.common.enums.EmKafkaMessageTopic
import cn.edu.lnnu.gis.rookie.base.common.sink.HttpAsyncSink
import cn.edu.lnnu.gis.rookie.realtime.position.process.{ClassificationPointsProcess, MyRedisMapper, ReduceProcessFuction}
import com.google.gson.Gson
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig

import scala.collection.mutable.ArrayBuffer


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


//    val positionsDS: DataStream[Positions] = vehiclePositionDS
//      .keyBy(row => row.getDriverId)
//      .timeWindowAll(Time.seconds(30), Time.seconds(2))
//      .process(new ReduceProcessFuction())

    vehiclePositionDS.print("====>")

    /**
     * 将数据通过webSocket发送
     */
    //vehiclePositionDS.addSink(new HttpAsyncSink[VehiclePosition](Constant.WEBSOCKET_DASHBOARD_URL_VEHICLE_POSITION))
    vehiclePositionDS.addSink(new RedisSink[VehiclePosition](
      new FlinkJedisPoolConfig.Builder().setHost("linux01").setPort(6379).build(),
      new MyRedisMapper()
    ))


    env.execute("RealTimePositionServic")
  }

}
