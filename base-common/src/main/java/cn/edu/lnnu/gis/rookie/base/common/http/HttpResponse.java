package cn.edu.lnnu.gis.rookie.base.common.http;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 兼容Scala中调用
 * 对象需要public
 * @author leon
 * @ClassName HttpResponse.java
 * @createTime 2020年10月16日 17:53:00
 */
@Slf4j
public class HttpResponse {
    public Boolean success = false;
    public String message = "";
    public Integer code = 0;
    public JSONObject result = null;
    public Long timestamp = 0L;
}
