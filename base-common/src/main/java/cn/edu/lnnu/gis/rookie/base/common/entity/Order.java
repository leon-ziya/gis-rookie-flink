package cn.edu.lnnu.gis.rookie.base.common.entity;

import lombok.Data;

/**
 * 订单
 */
@Data
public class Order  {
    private String orderId;
    private Long startTime;
    private Long stopTime;
    private Double getInPositionLon;
    private Double getInPositionLat;
    private Double getOutPositionLon;
    private Double getOutPositionLat;
    private Double value;


}
