package cn.edu.lnnu.gis.rookie.base.common.entity;

import lombok.Data;

/**
 * @author leon
 * @ClassName StartPoint.java
 * @createTime 2021年02月20日 16:56:00
 */
@Data
public class StartPoint {
    private String orderId;
    private Point2D startPsition;

    public StartPoint(String orderId, Point2D startPsition) {
        this.orderId = orderId;
        this.startPsition = startPsition;
    }

    public StartPoint() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Point2D getStartPsition() {
        return startPsition;
    }

    public void setStartPsition(Point2D startPsition) {
        this.startPsition = startPsition;
    }
}
