
## Terra-CCU实时数据处理

### 技术架构
* 开发语言
    * scala, 2.12.12  
    * kafka, 待确认线上
    * zookeeper, 待确认线上

### 服务操作
* 启动 zookeeper
```shell script
./bin/zkServer.sh conf/zoo.cfg
```

* kafka ipv6 禁用
```shell script
# vim /etc/sysctl.conf

  vm.max_map_count = 262144
  net.ipv6.conf.all.disable_ipv6 = 1

# sysctl -p /etc/sysctl.conf
```

* kafka 配置文件
```shell script
cat fig/server.properties
  listeners=PLAINTEXT://10.60.170.1:9092
```

* 启动 kafka-server
```shell script
./bin/kafka-server-start.sh config/server.properties
```

* 创建 topic
```shell script
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic CollectionMessage
```

* 查看 topic
```shell script
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

* 发送消息
```shell script
bin/kafka-console-producer.sh --bootstrap-server ip:9092 --topic CollectionMessage
```

* 消费消息
```shell script
bin/kafka-console-consumer.sh --bootstrap-server ip:9092 --topic CollectionMessage --from-beginning
```


## 数据模拟发送

```shell script
ls -l data/modules_map_tools_hdmap_generator_xinghe_DCU_recorded_trajectory_1577781377.csv

java -cp data-simulator-1.0-SNAPSHOT.jar cn.edu.cuhk.terra.ccu.flink.data.simulator.kafka.VehiclePositionWriter

java -jar data-simulator-1.0-SNAPSHOT-jar-with-dependencies.jar
```

