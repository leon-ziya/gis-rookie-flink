package cn.edu.cuhk.terra.ccu.flink.data.simulator.kafka

import java.io.File
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.io.Source

/**
 * @Author ShellMount
 * @Date 2020/7/24
 **/
object VehiclePositionWriter {
  def main(args: Array[String]): Unit = {
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "10.60.170.1:9092")
    properties.put("acks", "all")
    // properties.setProperty("group.id", "flink-consumer-group")
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    // properties.setProperty("auto.offset.reset", "latest")

    writeToKafka(properties)
  }

  def writeToKafka(properties: Properties): Unit = {
    val csvFileName = "data/modules_map_tools_hdmap_generator_xinghe_DCU_recorded_trajectory_1577781377.csv"
    val file = new File(csvFileName)
    println(file.getAbsolutePath)
    val bufferSource = Source.fromFile(file.getAbsolutePath)

    val producer = new KafkaProducer[String, String](properties)
    while (true) {
      for (line <- bufferSource.getLines()) {
        val record = new ProducerRecord[String, String]("CollectionMessage", line)
        producer.send(record)
        Thread.sleep(1000 * 1)
      }
    }

    producer.close()
  }

}
