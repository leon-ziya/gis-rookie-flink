package cn.edu.lnnu.gis.rookie.realtime.position.process

import cn.edu.lnnu.gis.rookie.base.common.entity.{Positions, VehiclePosition}
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}

/**
 * @ClassName MyRedisMapper.java
 * @author leon
 * @createTime 2021年02月09日 22:08:00
 */
class MyRedisMapper extends RedisMapper[VehiclePosition]{
  override def getCommandDescription: RedisCommandDescription = {
    new RedisCommandDescription(RedisCommand.HSET, "vehicleRealtimePosition")
  }

  override def getKeyFromData(data: VehiclePosition): String = data.getDriverId

  override def getValueFromData(data: VehiclePosition): String = data.getLongitude.toString + ", "+ data.getLatitude.toString
}
