package cn.edu.lnnu.gis.rookie.base.common.sink

import cn.edu.lnnu.gis.rookie.base.common.http.HttpClientAsyncRecall
import cn.edu.lnnu.gis.rookie.base.common.websocket.HttpMessageSender
import cn.hutool.core.date.{DatePattern, DateTime}
import com.alibaba.fastjson.{JSON, JSONObject}
import com.google.gson.Gson
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}

/**
 * 按需投递轨迹到websocket
 * @ClassName HttpAsyncSink.java
 * @author leon
 * @createTime 2020年10月16日 15:13:00
 */
class HttpAsyncSink[T](url:String) extends RichSinkFunction[T]{

  // 初始化过程
  override def open(parameters: Configuration): Unit = {
    super.open(parameters)
    HttpMessageSender.setRecallFunction(new HttpClientAsyncRecall())
  }

  // 调用链接执行
  override def invoke(value:T, context: SinkFunction.Context[_]): Unit ={
    val postBody: JSONObject = JSON.parseObject(new Gson().toJson(value))
    if(!postBody.containsKey("createTime")){
      postBody.put("createTime", DateTime.now().toString(DatePattern.NORM_DATETIME_FORMAT))
    }
    HttpMessageSender.sendAsyncPostMessage(url, postBody)
  }
}
