package cn.edu.lnnu.gis.rookie.base.common.websocket;

import cn.edu.lnnu.gis.rookie.base.common.http.HttpClientAsyncRecall;
import cn.edu.lnnu.gis.rookie.base.common.http.HttpClientService;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;

/**
 * http client使用
 * @author leon
 * @ClassName HttpMessageSender.java
 * @createTime 2020年10月16日 15:16:00
 */
public class HttpMessageSender {
    private static HttpClientAsyncRecall httpClientAsyncRecall = null;

    public static void setRecallFunction(HttpClientAsyncRecall recallFunction){
        httpClientAsyncRecall = recallFunction;
    }

    public static void sendAsyncPostMessage(String baseUrl, JSONObject postBody){
        Assert.notNull(httpClientAsyncRecall);
        try{
            HttpClientService.execAsyncReq(baseUrl, true, null, postBody, httpClientAsyncRecall);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
