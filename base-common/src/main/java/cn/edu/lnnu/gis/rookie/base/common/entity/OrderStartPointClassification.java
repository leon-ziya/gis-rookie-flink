package cn.edu.lnnu.gis.rookie.base.common.entity;

import lombok.Data;

/**
 * @author leon
 * @ClassName OrderStartPointClassification.java
 * @createTime 2021年02月20日 16:48:00
 */
@Data
public class OrderStartPointClassification {
    private String orderId;
    private Double longitude;
    private Double latitude;
    private Integer cluster;
    private Integer types;

    public OrderStartPointClassification(String orderId, Double longitude, Double latitude, Integer cluster, Integer types) {
        this.orderId = orderId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cluster = cluster;
        this.types = types;
    }

    public OrderStartPointClassification() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getCluster() {
        return cluster;
    }

    public void setCluster(Integer cluster) {
        this.cluster = cluster;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }
}
