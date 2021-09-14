package cn.edu.lnnu.gis.rookie.order.start.point.process

import java.{lang, util}
import java.util.ArrayList

import cn.edu.lnnu.gis.rookie.base.common.entity.{VehicleOrder, VehicleOrderPGis}
import org.apache.flink.streaming.api.scala.function.ProcessAllWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @ClassName VehicleOrderWindowProcess.java
 * @author leon
 * @createTime 2021年02月26日 12:56:00
 */
class VehicleOrderWindowProcess extends ProcessAllWindowFunction[VehicleOrderPGis, ArrayList[VehicleOrderPGis], TimeWindow]{
  override def process(
                        context: Context,
                        elements: Iterable[VehicleOrderPGis],
                        out: Collector[util.ArrayList[VehicleOrderPGis]]): Unit = {
    val vehicleOrderInter: Iterator[VehicleOrderPGis] = elements.iterator
    val vehicleOrderArr = new ArrayList[VehicleOrderPGis]
    while(vehicleOrderInter.hasNext){
      val vehicleOrderPGis: VehicleOrderPGis = vehicleOrderInter.next()
      println("订单数据： "+ vehicleOrderPGis)
      vehicleOrderArr.add(vehicleOrderPGis)
    }
    out.collect(vehicleOrderArr)
  }
}
