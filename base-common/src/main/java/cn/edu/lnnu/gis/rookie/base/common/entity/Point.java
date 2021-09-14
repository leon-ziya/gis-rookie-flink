package cn.edu.lnnu.gis.rookie.base.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author leon
 * @ClassName Point.java
 * @createTime 2021年02月10日 17:11:00
 */
@Data
public class Point implements Serializable {
    private Double longitude;
    private Double latitude;

    public Point() {
    }

    public Point(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
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

}
