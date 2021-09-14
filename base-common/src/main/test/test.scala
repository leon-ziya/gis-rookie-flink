import cn.edu.lnnu.gis.rookie.base.common.DateAndTimeUtils
import org.apache.commons.net.ntp.TimeStamp

/**
 * @ClassName test.java
 * @author leon
 * @createTime 2021年02月09日 17:45:00
 */
object test {

  private val utils = new DateAndTimeUtils

  def main(args: Array[String]): Unit = {
    val tm = "1478004562000,"
    val i: Int = tm.lastIndexOf(',')
    val str: String = tm.substring(0, i)
    print("结果："+str)
  }
}
