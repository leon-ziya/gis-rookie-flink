package cn.edu.lnnu.gis.rookie.base.common.constant;

/**
 * @author leon
 * @ClassName Constant.java
 * @createTime 2020年10月16日 09:29:00
 */
public interface Constant {
    /**
     * kafka bootStrap services
     */
    String KAFKA_BOOTSTRAP_SERVERS = "114.67.104.135:9092";

    /**
     * Redis链接服务器及端口
     */
    String REDIS_CONNECTING_SERVERS = "114.67.104.135";
    int REDIS_CONNECT_PORT = 6379;

    /**
     * Zookeeper配置
     */
    String ZOOKEEPER_QUORUM = "114.67.104.135";
    String ZOOKEEPER_CLIENT_PORT = "2181";


    /**
     * JDBC MYSQL 连接
     */
    String JDBC_MYSQL_URL = "jdbc:mysql://114.67.104.135:3306/jeecg-boot?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "jianglai";
    String MYSQL_DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";

    /**
     * WEBSOCKET 发送消息URL
     */
    String WEBSOCKET_DASHBOARD_URL_VEHICLE_POSITION = "http://127.0.0.1:8080/gis-rookie/websocket/dashboard/vehicle/position/";

    String WEBSOCKET_DASHBOARD_URL_VEHICLE_CLASSCIFICATION_POSITION = "http://127.0.0.1:8080/gis-rookie/websocket/dashboard/vehicle/classification/position/";
}
