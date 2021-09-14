package cn.edu.lnnu.gis.rookie.service.desk

import java.util

import cn.edu.lnnu.gis.rookie.base.common.config.KafkaConfig
import cn.edu.lnnu.gis.rookie.base.common.entity.{StartPoint, VehicleOrder}
import cn.edu.lnnu.gis.rookie.base.common.enums.EmKafkaMessageTopic
import cn.edu.lnnu.gis.rookie.service.desk.process.{ForwardFunction, MyFlatmap}
import com.google.gson.Gson
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
 * @ClassName ServiceDesk.java
 * @author leon
 * @createTime 2021年02月20日 23:43:00
 */
object ServiceDesk {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)


    /**
     * 读取kafka中GPS数据
     */
    val startPointListDs: DataStream[util.ArrayList[StartPoint]] = env.addSource(new FlinkKafkaConsumer(
      EmKafkaMessageTopic.KAFKA_COMMAND_SERVICE_DESK.getTpoic,
      new SimpleStringSchema(),
      KafkaConfig.getProperties
    )).map(row => new Gson().fromJson(row, classOf[util.ArrayList[StartPoint]]))

    val value: DataStream[StartPoint] = startPointListDs.flatMap(new MyFlatmap())

    value.process(new ForwardFunction())
  }
}
