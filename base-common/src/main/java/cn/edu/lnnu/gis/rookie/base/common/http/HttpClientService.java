package cn.edu.lnnu.gis.rookie.base.common.http;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

/**
 * http client 业务处理逻辑
 * @author leon
 * @ClassName HttpClientService.java
 * @createTime 2020年10月16日 17:44:00
 */
@Slf4j
public class HttpClientService {
    public static void execAsyncReq(String baseUrl,
                                    boolean isPost,
                                    List<BasicNameValuePair> urlParams,
                                    JSONObject postBody,
                                    FutureCallback callback
                                    ) throws Exception {
        if(baseUrl == null){
            log.warn("we don't have base url, check config");
            throw  new Exception("missing base url");
        }

        CloseableHttpAsyncClient hc = HttpClientFactory.getInstance().getHttpAsyncClientPool().getAsyncHttpClient();
        hc.start();

        HttpRequestBase httpMethod;

        try {
            HttpClientContext localContext = HttpClientContext.create();
            BasicCookieStore cookieStore = new BasicCookieStore();
            localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

            if (isPost) {
                httpMethod = new HttpPost(baseUrl);
                httpMethod.addHeader("Content-type", "application/json; charset=utf-8");
                httpMethod.addHeader("Accept", "application/json");

                if (null != postBody) {
                    log.debug("exeAsyncReq post postBody={}", postBody);
                    ((HttpPost) httpMethod).setEntity(new StringEntity(postBody.toJSONString(), Charset.forName("UTF-8")));
                }

                if (null != urlParams) {
                    String getUrl = EntityUtils.toString(new UrlEncodedFormEntity(urlParams));
                    httpMethod.setURI(new URI(httpMethod.getURI().toString() + "?" + getUrl));
                }
            } else {
                httpMethod = new HttpGet(baseUrl);
                if (null != urlParams) {
                    String getUrl = EntityUtils.toString(new UrlEncodedFormEntity(urlParams));
                    httpMethod.setURI(new URI(httpMethod.getURI().toString() + "?" + getUrl));
                }
            }

            hc.execute(httpMethod, localContext, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启用 get
     *
     * @param url
     * @param parameters
     * @return
     */
    @Deprecated
    public static String execSyncGet(String url, List<BasicNameValuePair> parameters) {
        return HttpClientFactory.getInstance().getHttpSyncClientPool().httpGet(url, parameters);
    }

    /**
     * 启用 post
     *
     * @param url
     * @param parameters
     * @param requestBody
     * @return
     */
    @Deprecated
    public static String execSyncPost(String url, List<BasicNameValuePair> parameters, String requestBody) {
        return HttpClientFactory.getInstance().getHttpSyncClientPool().httpPost(url, parameters, requestBody);
    }

    public static HttpResponse get(String url, List<BasicNameValuePair> parameters) {
        String responseString = HttpClientFactory.getInstance().getHttpSyncClientPool().httpGet(url, parameters);

        HttpResponse httpResponse = new HttpResponse();

        if (responseString.isEmpty() || responseString == null) {
            return httpResponse;
        }

        httpResponse = new Gson().fromJson(responseString, HttpResponse.class);

        return httpResponse;
    }

    public static HttpResponse post(String url, List<BasicNameValuePair> parameters, JSONObject requestBody) {
        Assert.notNull(requestBody);

        String responseString = HttpClientFactory.getInstance().getHttpSyncClientPool().httpPost(url, parameters, requestBody.toJSONString());

        HttpResponse httpResponse = new HttpResponse();

        if (responseString.isEmpty() || responseString == null) {
            return httpResponse;
        }

        httpResponse = new Gson().fromJson(responseString, HttpResponse.class);

        return httpResponse;
    }
}
