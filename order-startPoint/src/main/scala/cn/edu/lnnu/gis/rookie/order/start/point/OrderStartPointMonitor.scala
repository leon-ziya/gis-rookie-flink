package cn.edu.lnnu.gis.rookie.order.start.point

import java.util
import cn.edu.lnnu.gis.rookie.base.common.config.KafkaConfig
import cn.edu.lnnu.gis.rookie.base.common.constant.Constant
import cn.edu.lnnu.gis.rookie.base.common.entity.{StartPoint, VehicleOrder}
import cn.edu.lnnu.gis.rookie.base.common.enums.EmKafkaMessageTopic
import cn.edu.lnnu.gis.rookie.order.start.point.process.OrderStartPointReduceProcess
import com.google.gson.Gson
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}

/**
 * @ClassName OrderStartPointMonitor.java
 * @author leon
 * @createTime 2021年02月20日 23:17:00
 */
object OrderStartPointMonitor {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    /**
     * 读取kafka中GPS数据
     */
    val orderDS: DataStream[VehicleOrder] = env.addSource(new FlinkKafkaConsumer(
      EmKafkaMessageTopic.KAFKA_SIMULATOR_VEHICLE_ORDER.getTpoic,
      new SimpleStringSchema(),
      KafkaConfig.getProperties
    )).map(row => new Gson().fromJson(row, classOf[VehicleOrder]))

    /**
     * TODO： 指定事件时间
     */
    val eventStampVehicleOrderDS: DataStream[VehicleOrder] = orderDS.assignTimestampsAndWatermarks(WatermarkStrategy.forMonotonousTimestamps[VehicleOrder].withTimestampAssigner(
      new SerializableTimestampAssigner[VehicleOrder] {
        override def extractTimestamp(element: VehicleOrder, recordTimestamp: Long): Long = {
          element.getStartTimeStamp.toLong
        }
      }
    ))

    /**
     * 窗口处理数据
     */
    val commandString: DataStream[String] = eventStampVehicleOrderDS.process(new OrderStartPointReduceProcess())

    /**
     * 输出
     */
    commandString.print("时间： ")
    env.execute("OrderStartPointMointor")

  }
}
