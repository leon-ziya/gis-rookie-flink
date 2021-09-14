package cn.edu.lnnu.gis.rookie.base.common.entity;

import lombok.Data;

/**
 * 订单
 */
@Data
public class VehicleOrder {
    private String orderId;
    private String startTimeStamp;
    private String endTimeStamp;
    private Double getOnLongitude;
    private Double getOnLatitude;
    private Double getOffLongitude;
    private Double getOffLatitude;
    private Double getOnX;
    private Double getOnY;
    private Double getOffX;
    private Double getOffY;
    private Double value;
    private String startTime;
    private String endTime;
    private boolean orderStart;
    private boolean orderEnd;

    public VehicleOrder() {
    }

    public VehicleOrder(String orderId,
                        String startTimeStamp,
                        String endTimeStamp,
                        Double getOnLongitude,
                        Double getOnLatitude,
                        Double getOffLongitude,
                        Double getOffLatitude,
                        Double getOnX,
                        Double getOnY,
                        Double getOffX,
                        Double getOffY,
                        Double value,
                        String startTime,
                        String endTime,
                        boolean orderStart,
                        boolean orderEnd) {
        this.orderId = orderId;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.getOnLongitude = getOnLongitude;
        this.getOnLatitude = getOnLatitude;
        this.getOffLongitude = getOffLongitude;
        this.getOffLatitude = getOffLatitude;
        this.getOnX = getOnX;
        this.getOnY = getOnY;
        this.getOffX = getOffX;
        this.getOffY = getOffY;
        this.value = value;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderStart = orderStart;
        this.orderEnd = orderEnd;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public Double getGetOnLongitude() {
        return getOnLongitude;
    }

    public void setGetOnLongitude(Double getOnLongitude) {
        this.getOnLongitude = getOnLongitude;
    }

    public Double getGetOnLatitude() {
        return getOnLatitude;
    }

    public void setGetOnLatitude(Double getOnLatitude) {
        this.getOnLatitude = getOnLatitude;
    }

    public Double getGetOffLongitude() {
        return getOffLongitude;
    }

    public void setGetOffLongitude(Double getOffLongitude) {
        this.getOffLongitude = getOffLongitude;
    }

    public Double getGetOffLatitude() {
        return getOffLatitude;
    }

    public void setGetOffLatitude(Double getOffLatitude) {
        this.getOffLatitude = getOffLatitude;
    }

    public Double getGetOnX() {
        return getOnX;
    }

    public void setGetOnX(Double getOnX) {
        this.getOnX = getOnX;
    }

    public Double getGetOnY() {
        return getOnY;
    }

    public void setGetOnY(Double getOnY) {
        this.getOnY = getOnY;
    }

    public Double getGetOffX() {
        return getOffX;
    }

    public void setGetOffX(Double getOffX) {
        this.getOffX = getOffX;
    }

    public Double getGetOffY() {
        return getOffY;
    }

    public void setGetOffY(Double getOffY) {
        this.getOffY = getOffY;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isOrderStart() {
        return orderStart;
    }

    public void setOrderStart(boolean orderStart) {
        this.orderStart = orderStart;
    }

    public boolean isOrderEnd() {
        return orderEnd;
    }

    public void setOrderEnd(boolean orderEnd) {
        this.orderEnd = orderEnd;
    }
}
