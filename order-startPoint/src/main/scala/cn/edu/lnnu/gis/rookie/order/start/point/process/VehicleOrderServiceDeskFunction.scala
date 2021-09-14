package cn.edu.lnnu.gis.rookie.order.start.point.process

import java.util
import java.util.ArrayList

import cn.edu.lnnu.gis.rookie.base.common.entity.{StartPoint, VehicleOrderPGis}
import cn.edu.lnnu.gis.rookie.base.common.http.{HttpClientService, HttpResponse}
import com.alibaba.fastjson.{JSON, JSONObject}
import com.google.gson.Gson
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.util.Collector

/**
 * @ClassName VehicleOrderServiceDeskFunction.java
 * @author leon
 * @createTime 2021年02月26日 15:39:00
 */
class VehicleOrderServiceDeskFunction extends ProcessFunction[ArrayList[VehicleOrderPGis], ArrayList[VehicleOrderPGis]]{
  override def processElement(
                               value: util.ArrayList[VehicleOrderPGis],
                               ctx: ProcessFunction[util.ArrayList[VehicleOrderPGis], util.ArrayList[VehicleOrderPGis]]#Context,
                               out: Collector[util.ArrayList[VehicleOrderPGis]]): Unit = {
    val orderList = new ArrayList[String]
    value.forEach(i => {
      orderList.add(i.getOrderId)
    })

    sendRequest("", orderList)

  }

  /**
   * 服务台转发请求
   * @param url
   * @param orderList
   * @return
   */
  private def sendRequest(url:String, orderList: ArrayList[String]): String ={
    val postBody: JSONObject = JSON.parseObject(new Gson().toJson(orderList))
    val response: HttpResponse = HttpClientService.post(url, null, postBody)
    // 网络亲求失败
    if(response.code == 0) return ""

    // 业务处理失败
    if (!response.success) return ""

    // 无响应结果
    if (response.result == null) return ""

    response.result.toString()

  }
}
