package cn.edu.lnnu.gis.rookie.order.track.process

import java.text.SimpleDateFormat
import java.{lang, util}

import cn.edu.lnnu.gis.rookie.base.common.entity.{OrderTrack, Point, Point2D, VehicleOrder, VehiclePosition}
import cn.hutool.log.Log
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor, ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction
import org.apache.flink.util.Collector

/**
 * @ClassName OrderAndPositionConnectProcess.java
 * @author leon
 * @createTime 2021年02月10日 20:05:00
 */

class OrderAndPositionConnectProcess extends KeyedCoProcessFunction[String, VehiclePosition, VehicleOrder, OrderTrack]{
  // lazy val vehicleState: ValueState[Vehicle] = getRuntimeContext.getState(new ValueStateDescriptor[Vehicle]("DefaultVehicle", classOf[Vehicle]))
  lazy val vehicleOrderState: ValueState[VehicleOrder] = getRuntimeContext.getState(new ValueStateDescriptor[VehicleOrder]("VehicleOrder", classOf[VehicleOrder]))
  lazy val trackPositionList: ListState[Point] = getRuntimeContext.getListState(new ListStateDescriptor[Point]("PointListState", classOf[Point]))

  override def processElement1(
                                value: VehiclePosition,
                                ctx: KeyedCoProcessFunction[String, VehiclePosition, VehicleOrder, OrderTrack]#Context,
                                out: Collector[OrderTrack]): Unit = {

    if(!(vehicleOrderState.value() == null)){
      val longitude: Double = value.getLongitude
      val latitude: Double = value.getLatitude
      val point = new Point(longitude, latitude)
      trackPositionList.add(point)
    }
  }

  override def processElement2(
                                value: VehicleOrder,
                                ctx: KeyedCoProcessFunction[String, VehiclePosition, VehicleOrder, OrderTrack]#Context,
                                out: Collector[OrderTrack]): Unit = {
    var sdf:SimpleDateFormat = new SimpleDateFormat("HH:mm:ss")
    println("数据来了==》"+value.toString)
    // 当车辆订单计算状态为空并且订单开始，则更新车辆订单计算状态
    if(vehicleOrderState.value() == null && value.isOrderStart){
        vehicleOrderState.update(value)
      println("订单："+value.getOrderId+"==>"+sdf.format(value.getStartTimeStamp.toLong)+"==>"+sdf.format(value.getEndTimeStamp)+" =====>开始")
    }
    if(value.isOrderEnd && !(vehicleOrderState.value() == null) && !(trackPositionList.get() == null)){
      Thread.sleep(30000)
      val track = new util.ArrayList[Point2D]()
      val points: lang.Iterable[Point] = trackPositionList.get()
      points.forEach(point => {
        val point2D: Point2D = Point2D(point.getLongitude, point.getLatitude)
        track.add(point2D)
      })
      val endTime: Int = vehicleOrderState.value().getEndTimeStamp.toInt
      val startTime: Int = vehicleOrderState.value().getStartTimeStamp.toInt
      val timeGap: Float = (endTime - startTime).toFloat / 60
//      if(track.size() > 50 && timeGap > 5 ){
//        println("订单："+value.getOrderId+" =====>结束")
//        val resultTrack = OrderTrack(vehicleOrderState.value().getOrderId, track)
//        out.collect(resultTrack)
//      }
      println("订单："+value.getOrderId+"==>"+sdf.format(value.getStartTimeStamp.toLong)+"==>"+sdf.format(value.getEndTimeStamp)+" =====>结束")
      val resultTrack = OrderTrack(vehicleOrderState.value().getOrderId, track)
      out.collect(resultTrack)
      vehicleOrderState.clear()
    }
  }
}
