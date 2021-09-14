package cn.edu.lnnu.gis.rookie.base.common

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @ClassName DataAndTimeUtils.java
 * @author leon
 * @createTime 2021年02月09日 17:39:00
 */
case class DateAndTimeUtils() {

  /**
   * 时间戳转化为详细日期
   * @param tm
   */
    def tranTimeToString(tm: String)={
      val fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val dateTime: String = fm.format(new Date(tm.toLong))
      dateTime
    }

}
