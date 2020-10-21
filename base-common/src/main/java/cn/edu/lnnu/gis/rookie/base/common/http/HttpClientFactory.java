package cn.edu.lnnu.gis.rookie.base.common.http;

/**
 * @author leon
 * @ClassName HttpClientFactory.java
 * @createTime 2020年10月16日 17:49:00
 */
public class HttpClientFactory {
    private static HttpClientAsyncUtil httpAsyncClient = new HttpClientAsyncUtil();
    private static HttpClientSyncUtil httpSyncClient = new HttpClientSyncUtil();
    private static HttpClientFactory httpClientFactory = new HttpClientFactory();

    private HttpClientFactory() {
    }

    public static HttpClientFactory getInstance() {
        return httpClientFactory;
    }

    public HttpClientAsyncUtil getHttpAsyncClientPool() {
        return httpAsyncClient;
    }

    public HttpClientSyncUtil getHttpSyncClientPool() {
        return httpSyncClient;
    }
}
