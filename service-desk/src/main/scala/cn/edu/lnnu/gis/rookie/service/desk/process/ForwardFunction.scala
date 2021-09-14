package cn.edu.lnnu.gis.rookie.service.desk.process

import cn.edu.lnnu.gis.rookie.base.common.entity.StartPoint
import cn.edu.lnnu.gis.rookie.base.common.http.{HttpClientService, HttpResponse}
import com.alibaba.fastjson.{JSON, JSONObject}
import com.google.gson.Gson
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.util.Collector

/**
 * @ClassName ForwardFunction.java
 * @author leon
 * @createTime 2021年02月21日 00:01:00
 */
class ForwardFunction extends ProcessFunction[StartPoint, StartPoint]{
  override def processElement(
                               value: StartPoint,
                               ctx: ProcessFunction[StartPoint, StartPoint]#Context,
                               out: Collector[StartPoint]): Unit = {
    sendRequest()
  }

  /**
   * 服务台转发请求
   * @param url
   * @param point
   * @return
   */
  private def sendRequest(url:String, point: StartPoint): String ={
    val postBody: JSONObject = JSON.parseObject(new Gson().toJson(point))
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
