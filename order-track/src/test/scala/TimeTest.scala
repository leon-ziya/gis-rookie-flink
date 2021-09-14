import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

/**
 * @ClassName TimeTest.java
 * @author leon
 * @createTime 2021年02月17日 14:29:00
 */
object TimeTest {
  def main(args: Array[String]): Unit = {


//    val startTimeStamp = 1477964797L
//    val endTimeStamp = 1477987675L
//    val startTime: String = timeFormat(startTimeStamp)
//    val endTime: String = timeFormat(endTimeStamp)
//    println("startDate==>"+startTime)
//    println("endDate==>"+endTime)
//    println("差值==》"+(endTimeStamp.toInt - startTimeStamp.toInt))
//    println("差值==》"+(((endTimeStamp.toInt - startTimeStamp.toInt).toFloat/60)))
//    val sdf = new SimpleDateFormat("HH:mm:ss")
//    val startTime: String = sdf.format(startTimeStamp)
//    val endTime: String = sdf.format(endTimeStamp)
    val startTime = "18:04:32"
    val endTime = "18:04:50"
    println("开始时间==："+startTime)
    println("结束时间==："+endTime)
    val i: Int = endTime.compareTo(startTime)
    if(i>0)println(endTime+" 大于 "+ startTime)
    if(i==0) println(endTime+" 等于 "+ startTime)
    if(i<0) println(endTime+" 小于 "+ startTime)
  }

  def timeFormat(time:String):String={
    var sdf:SimpleDateFormat = new SimpleDateFormat("HH:mm:ss")
    val str: String = sdf.format(time)
    str
  }
}
