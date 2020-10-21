package cn.edu.lnnu.gis.rookie.base.common.enums;

/**
 * @author leon
 * @ClassName EmKafkaMessageTopic.java
 * @createTime 2020年10月16日 10:29:00
 */
public enum EmKafkaMessageTopic {
    //订单-----topic
    KAFKA_SIMULATOR_VEHICLE_ORDER("BASE", "vehicle-order"),
    //实时位置---topic
    KAFKA_SIMULATOR_VEHICLE_POSITION("BASE", "vehicle-position"),
    ;


    /**
     * 消息类型
     */
    private String messageType;
    /**
     * kafka topic
     */
    private String tpoic;

    EmKafkaMessageTopic(String messageType, String tpoic) {
        this.messageType = messageType;
        this.tpoic = tpoic;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTpoic() {
        return tpoic;
    }

    public void setTpoic(String tpoic) {
        this.tpoic = tpoic;
    }
}
