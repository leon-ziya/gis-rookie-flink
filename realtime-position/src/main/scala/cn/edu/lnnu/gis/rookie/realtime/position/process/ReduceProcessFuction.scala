package cn.edu.lnnu.gis.rookie.realtime.position.process

import java.util

import cn.edu.lnnu.gis.rookie.base.common.entity.{Positions, VehiclePosition}
import org.apache.flink.streaming.api.scala.function.{ProcessAllWindowFunction, ProcessWindowFunction}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @ClassName ReduceProcessFuction.java
 * @author leon
 * @createTime 2020年10月21日 10:10:00
 */
class ReduceProcessFuction extends ProcessAllWindowFunction[VehiclePosition, Positions, TimeWindow]{
  override def process(context: Context, elements: Iterable[VehiclePosition], collector: Collector[Positions]): Unit = {
    val positions = new Positions()
    val positionList = new util.ArrayList[VehiclePosition]()
    for(element <- elements){
      positionList.add(element)
    }
    positions.setVehiclePositions(positionList)
    collector.collect(positions)
  }
}
