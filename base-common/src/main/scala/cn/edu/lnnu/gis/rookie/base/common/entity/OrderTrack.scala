package cn.edu.lnnu.gis.rookie.base.common.entity

import java.util

/**
 * @ClassName OrderTrack.java
 * @author leon
 * @createTime 2021年02月10日 15:28:00
 */
case class OrderTrack(
                     orderId: String,
                     track: util.ArrayList[Point2D]
                     )
