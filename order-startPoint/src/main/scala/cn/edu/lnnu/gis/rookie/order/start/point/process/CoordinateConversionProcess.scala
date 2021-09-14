package cn.edu.lnnu.gis.rookie.order.start.point.process

import cn.edu.lnnu.gis.rookie.base.common.entity.{Point2D, VehicleOrder, VehicleOrderPGis}
import cn.edu.lnnu.gis.rookie.base.common.utils.GPSUtil
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.util.Collector
import org.postgresql.util.PGTimestamp

/**
 * @ClassName CoordinateConversionProcess.java
 * @author leon
 * @createTime 2021年02月26日 12:26:00
 */
class CoordinateConversionProcess extends ProcessFunction[VehicleOrder, VehicleOrderPGis]{
  override def processElement(
                               value: VehicleOrder,
                               ctx: ProcessFunction[VehicleOrder, VehicleOrderPGis]#Context,
                               out: Collector[VehicleOrderPGis]): Unit = {
    val gpsUtil = new GPSUtil
    val startPointXY: Array[Double] = gpsUtil.gcj02_To_Gps84(value.getGetOnLatitude, value.getGetOnLongitude)
    val endPointXY: Array[Double] = gpsUtil.gcj02_To_Gps84(value.getGetOffLatitude, value.getGetOffLongitude)

    val vehicleOrderPGis = new VehicleOrderPGis(
      value.getOrderId,
      Point2D(startPointXY(1), startPointXY(0)),
      Point2D(endPointXY(1), endPointXY(0)),
      new PGTimestamp(value.getStartTimeStamp.toLong*1000),
      new PGTimestamp(value.getEndTimeStamp.toLong*1000)
    )
    out.collect(vehicleOrderPGis)
  }
}
