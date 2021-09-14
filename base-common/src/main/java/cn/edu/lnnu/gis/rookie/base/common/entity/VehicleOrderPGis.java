package cn.edu.lnnu.gis.rookie.base.common.entity;

import lombok.Data;
import org.postgresql.util.PGTimestamp;

/**
 * @author leon
 * @ClassName VehicleOrderPGis.java
 * @createTime 2021年02月26日 10:33:00
 */
@Data
public class VehicleOrderPGis {
    private String orderId;
    private Point2D startPoint;
    private Point2D endPoint;
    private PGTimestamp startTime;
    private PGTimestamp endTime;

    public VehicleOrderPGis(String orderId, Point2D startPoint, Point2D endPoint, PGTimestamp startTime, PGTimestamp endTime) {
        this.orderId = orderId;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public VehicleOrderPGis() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Point2D getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D startPoint) {
        this.startPoint = startPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    public PGTimestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(PGTimestamp startTime) {
        this.startTime = startTime;
    }

    public PGTimestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(PGTimestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "VehicleOrderPGis{" +
                "orderId='" + orderId + '\'' +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
