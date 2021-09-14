package cn.edu.lnnu.gis.rookie.realtime.position

import cn.edu.lnnu.gis.rookie.base.common.config.KafkaConfig
import cn.edu.lnnu.gis.rookie.base.common.constant.Constant
import cn.edu.lnnu.gis.rookie.base.common.entity.{ClassificationPoints, ClassificationPositions, Positions, VehiclePosition}
import cn.edu.lnnu.gis.rookie.base.common.enums.EmKafkaMessageTopic
import cn.edu.lnnu.gis.rookie.base.common.sink.HttpAsyncSink
import cn.edu.lnnu.gis.rookie.realtime.position.process.{ClassificationPointsProcess, ReduceProcessFuction}
import com.google.gson.Gson
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

import scala.collection.mutable.ArrayBuffer

/**
 * @ClassName ClassificationPoints.java
 * @author leon
 * @createTime 2020年10月27日 11:14:00
 */
object ClassificationPoints {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)

    /**
     * 读取kafka中GPS数据
     */
    val vehiclePositionDS: DataStream[VehiclePosition] = env.addSource(new FlinkKafkaConsumer(
      EmKafkaMessageTopic.KAFKA_SIMULATOR_VEHICLE_POSITION.getTpoic,
      new SimpleStringSchema(),
      KafkaConfig.getProperties
    )).map(row => new Gson().fromJson(row, classOf[VehiclePosition]))

    /**
     * 划分窗口
     */
    val positionsDS: DataStream[Positions] = vehiclePositionDS
      .keyBy(row => row.getDriverId)
      .timeWindowAll(Time.seconds(5), Time.seconds(2))
      .process(new ReduceProcessFuction())

    /**
     * 将数据通过webSocket发送
     */
    val classificationPointsDS: DataStream[ClassificationPositions] = positionsDS.process(new ClassificationPointsProcess())
    classificationPointsDS.print("classification==>")

    classificationPointsDS.addSink(new HttpAsyncSink[ClassificationPositions](Constant.WEBSOCKET_DASHBOARD_URL_VEHICLE_CLASSCIFICATION_POSITION))

    env.execute("ClassificationPoints")
  }
}
