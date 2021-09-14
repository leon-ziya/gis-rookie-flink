package cn.edu.lnnu.gis.rookie.service.desk.process

import java.util

import cn.edu.lnnu.gis.rookie.base.common.entity.StartPoint
import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.util.Collector

/**
 * @ClassName MyFlatmap.java
 * @author leon
 * @createTime 2021年02月20日 23:53:00
 */
class MyFlatmap extends FlatMapFunction[util.ArrayList[StartPoint], StartPoint]{
  override def flatMap(value: util.ArrayList[StartPoint], out: Collector[StartPoint]): Unit = {
    for(i<- 0 to value.size()-1){
      val point: StartPoint = value.get(i)
      out.collect(point)
    }
  }
}
