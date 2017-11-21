package com.yonyou.appbase.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by qicen on 2017/3/9.
 */
public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 50;

    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 20;

    /**
     * 连接池管理器
     */
    private static PoolingHttpClientConnectionManager connManager;

    /**
     * 初始化
     */
    static {
        // 连接池管理器
        connManager = new PoolingHttpClientConnectionManager();
        // 连接池最大连接数
        connManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        // 每个路由最大连接数
        connManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);

        logger.info("Initializing CloseableHttpClient");
    }

    private static CloseableHttpClient getHttpClient() {
        // http请求配置信息
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(30000)
                .setConnectionRequestTimeout(30000).setCookieSpec(CookieSpecs.STANDARD).build();
        return HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig)
                .setConnectionManagerShared(true).build();
    }

    public static <T> T doGet(String url, Class<T> tClass) {
        try {
            String jsonStr= executeGet(url);
            T object = JSONObject.parseObject(jsonStr, tClass);
            return object;
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     *@Description:发送post请求
     *params: * @param null
     *@Author:sunshine
     *@Date:2017/4/10 下午4:29
     *
     */
    public static <T> T doPost(String url, Map<String,String> params, Class<T> tClass) {
        try {
            String jsonStr=executePost(url,params);
            T object = JSONObject.parseObject(jsonStr, tClass);
            return object;
        } catch (Exception e) {
        	 logger.error("",e);
        	 return null;
        }
    }


    /**
     *@Description:发送post请求
     *params: * @param null
     *@Author:sunshine
     *@Date:2017/4/10 下午4:29
     *
     */
    public static <T> T doPost(String url, String reponseBody, Class<T> tClass) {
        try {
            HttpPost httpPost=new HttpPost(url);
            httpPost.setEntity(new StringEntity(reponseBody, ContentType.APPLICATION_JSON));
            String jsonStr=doExecute(httpPost,Charset.forName("UTF-8"));
            T object = JSONObject.parseObject(jsonStr, tClass);
            return object;
        } catch (Exception e) {
        	 logger.error("",e);
        	 return null;
        }
    }



    /**
     * 处理GET请求。
     *
     * @param url
     * @return
     */
    public static String executeGet(String url) throws IOException {
        return executeGet(url, null);
    }

    /**
     * 处理GET请求。
     *
     * @param url
     * @param params
     * @return
     */
    public static String executeGet(String url, Map<String, String> params) throws IOException {
        return executeGet(url, null, params);
    }

    /**
     * 处理GET请求。
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static String executeGet(String url, Map<String, String> headers, Map<String, String> params)
            throws IOException {
        return execute(url, HttpGet.METHOD_NAME, headers, params, null);
    }

    /**
     * 处理POST请求。
     *
     * @param url
     * @return
     */
    public static String executePost(String url) throws URISyntaxException, IOException {
        return executePost(url, null);
    }
    /**
     * 处理POST请求。
     *
     * @param url
     * @param params
     * @return
     */
    public static String executePost(String url, Map<String, String> params) throws IOException {
        return executePost(url, null, params);
    }

    /**
     * 处理POST请求。
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static String executePost(String url, Map<String, String> headers, Map<String, String> params)
            throws IOException {
        return execute(url, HttpPost.METHOD_NAME, headers, params, Charset.forName("UTF-8"));
    }

    /**
     * 处理HTTP请求。
     *
     * @param uri
     * @param method
     * @param headers
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static String execute(String uri, String method, Map<String, String> headers, Map<String, String> params,
                                 Charset charSet) throws IOException {
        // 构建http请求
        RequestBuilder requestBuilder = RequestBuilder.create(method).setUri(uri).setCharset(charSet);
        // 设置请求参数
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                requestBuilder.addParameter(param.getKey(), param.getValue());
            }
        }
        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }
        // 执行http请求
        return doExecute(requestBuilder.build(), charSet);
    }


    /**
     * 执行Http请求。
     *
     * @param request
     * @return
     */
    private static String doExecute(HttpUriRequest request, Charset charSet) throws IOException {
    	CloseableHttpResponse response = null;
        // 执行请求
        try{
        	response = getHttpClient().execute(request);
            int statusCode =response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.OK.value()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "utf-8");
            } else {
                logger.error("接口异常statusCode:"+statusCode);
                return null;
            }
        }catch(Exception e){
        	logger.error("",e);
        	if(response != null){
        		response.close();
        	}
        	return null;
        }
    }



}
