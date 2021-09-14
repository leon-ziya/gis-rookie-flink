package cn.edu.lnnu.gis.rookie.order.start.point.process

import cn.edu.lnnu.gis.rookie.base.common.entity.{Point2D, StartPoint, VehicleOrder}
import cn.hutool.core.util.ObjectUtil
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.util.Collector

import java.lang
import java.text.SimpleDateFormat
import java.util.Date

/**
 * @ClassName OrderStartPointReduceProcess.java
 * @author leon
 * @createTime 2021年02月20日 16:54:00
 */
class OrderStartPointReduceProcess extends ProcessFunction[VehicleOrder, String]{
  private var first = true
  override def processElement(value: VehicleOrder, ctx: ProcessFunction[VehicleOrder, String]#Context, out: Collector[String]): Unit = {
    var timeStampStr: String = ""
    val timeStamp: Long = value.getStartTimeStamp.toLong
    val date = new Date(timeStamp * 1000)
    val sdf = new SimpleDateFormat("HH:mm:ss")
    val timeString: String = sdf.format(date)
    val dateTimeStrArr: Array[String] = timeString.split(":")
    val hours: String = dateTimeStrArr(0)
    val minute = dateTimeStrArr(1)

    if(first){
      first = false

    }

  }
}
