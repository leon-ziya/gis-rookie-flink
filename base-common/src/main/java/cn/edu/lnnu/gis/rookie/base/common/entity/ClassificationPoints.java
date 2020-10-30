package cn.edu.lnnu.gis.rookie.base.common.entity;

import lombok.Data;
import scala.Int;

/**
 * @author leon
 * @ClassName ClassificationPoints.java
 * @createTime 2020年10月26日 19:57:00
 */
@Data
public class ClassificationPoints {
    private Double longitude;
    private Double latitude;
    private Integer cluster;
    private Integer types;
}
