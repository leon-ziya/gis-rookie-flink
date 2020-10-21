package cn.edu.lnnu.gis.rookie.base.common.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *
 * 被回调的对象， 给一部的httpclient使用
 * @author leon
 * @ClassName HttpClientAsyncRecall.java
 * @createTime 2020年10月16日 15:17:00
 */
@Slf4j
public class HttpClientAsyncRecall implements FutureCallback<HttpResponse> {

    /**
     * 请求完成后调用该函数
     * @param response
     */
    @Override
    public void completed(HttpResponse response) {
        doResponseAfterCompleted(response);
        HttpClientUtils.closeQuietly(response);
    }


    /**
     * 请求失败后调用该函数
     * @param e
     */
    @Override
    public void failed(Exception e) {
        log.debug("\n-----------------");
        log.debug("请求失败", e);
    }

    /**
     * 请求取消后调用该函数
     */
    @Override
    public void cancelled() {

    }

    private String doResponseAfterCompleted(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String body = null;
        if(entity == null){
            return null;
        }

        try{
            body = EntityUtils.toString(entity, "utf-8");
        }catch (ParseException e){
            System.out.println("the response's content inputstream is corrupt" + e);
        } catch (IOException e) {
            System.out.println("the response's content inputstream is corrupt" + e);
        }
        log.debug("\n---------------->");
        log.debug(body);

        return body;
    }
}
