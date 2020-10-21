package cn.edu.lnnu.gis.rookie.base.common.config;

import cn.edu.lnnu.gis.rookie.base.common.constant.Constant;

import java.util.Properties;

/**
 * kafka连接配置
 *
 * @author leon
 * @ClassName kafkaConfig.java
 * @createTime 2020年10月16日 09:26:00
 */
public class KafkaConfig {
    private static Properties properties = new Properties();
    private static Properties propertiesByteArray = new Properties();

    static {
        properties.setProperty("bootstrap.servers", Constant.KAFKA_BOOTSTRAP_SERVERS);
        properties.put("acks", "all");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("vaule.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("auto.offset.reset", "latest");
    }

    static {
        propertiesByteArray.setProperty("bootstrap.servers", Constant.KAFKA_BOOTSTRAP_SERVERS);
        propertiesByteArray.put("acks", "all");
        // properties.setProperty("group.id", "flink-consumer-group")
        propertiesByteArray.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        propertiesByteArray.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        propertiesByteArray.setProperty("auto.offset.reset", "latest");
    }

    public static Properties getProperties() {
        return properties;
    }

    public static Properties getPropertiesByteArray() {
        return propertiesByteArray;
    }
}
