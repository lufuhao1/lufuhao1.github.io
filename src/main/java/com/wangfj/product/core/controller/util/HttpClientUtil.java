package com.wangfj.product.core.controller.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangxuan on 2016-04-27 0027.
 */
public class HttpClientUtil {

    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    /**
     * Post Form请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String doPost(String url, Map<String, String> params, String charset) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        // 设置Http Post数据
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        if (params != null) {
            Set<String> keySet = params.keySet();
            NameValuePair[] param = new NameValuePair[keySet.size()];
            int i = 0;
            for (String key : keySet) {
                param[i] = new NameValuePair(key, params.get(key));
                i++;
            }
            method.setRequestBody(param);
        }
        InputStream responseBodyStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                responseBodyStream = method.getResponseBodyAsStream();
                streamReader = new InputStreamReader(responseBodyStream, charset);
                reader = new BufferedReader(streamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            try {
                responseBodyStream.close();
                streamReader.close();
                reader.close();
            } catch (IOException e) {
                logger.error("执行HTTP Post请求" + url + "时，发生异常，关闭流异常！", e);
                e.printStackTrace();
            }
            method.releaseConnection();
        }
        return response.toString();
    }

}
