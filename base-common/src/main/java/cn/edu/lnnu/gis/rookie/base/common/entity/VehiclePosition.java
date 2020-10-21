package cn.edu.lnnu.gis.rookie.base.common.entity;

import lombok.Data;

/**
 * 车辆实时位置
 */
@Data
public class VehiclePosition {
    private String driverId;
    private String orderId;
    private Long timeStamp;
    private Double longitude;
    private Double latitude;
}
