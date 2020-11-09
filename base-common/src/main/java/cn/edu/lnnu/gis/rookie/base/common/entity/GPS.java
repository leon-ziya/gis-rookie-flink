package cn.edu.lnnu.gis.rookie.base.common.entity;

/**
 * @author leon
 * @ClassName GPS.java
 * @createTime 2020年11月06日 16:22:00
 */
public class GPS {
    private double lat;
    private double lon;

    public GPS(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String toString() {
        return "lat:" + lat + "," + "lon:" + lon;
    }
}
