package com.louddt.tag.okhttp;

import com.louddt.tag.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component("okHttp3PostUtils")
public class OkHttp3PostUtilsImpl implements OkHttp3Utils {

    @Autowired
    @Qualifier("shortHttpClient")
    private OkHttpClient shortHttpClient;

    /**
     * Post请求
     *
     * @param url      请求地址 http://ip:port/context-path/request-mapping.json
     * @param jsonArgs json字符串格式的参数 {"username":typa1@qq.com,"password":123456}
     * @param tC       泛型类型
     * @param <T>      泛型标识
     * @return T 转换后的泛型类型对象
     * @throws RuntimeException 运行时异常
     */
    public <T> T request(String url, String jsonArgs, Class<T> tC) {
        try {
            RequestBody body = RequestBody.create(JSON, jsonArgs);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = shortHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return StringUtil.string2object(response.body().string(), tC);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Okhttp3POST请求异常", e);
        }
    }

    /**
     * Post请求
     *
     * @param url     请求地址 http://ip:port/context-path/request-mapping.json
     * @param mapArgs map字符串键值对封装的请求参数
     * @param tC      泛型类型
     * @param <T>     泛型标识
     * @return T 转换后的泛型类型对象
     * @throws RuntimeException 运行时异常
     */
    public <T> T request(String url, Map<String, String> mapArgs, Class<T> tC) {
        try {

            FormBody.Builder builder = new FormBody.Builder();
            if (mapArgs != null) {
                for (Map.Entry<String, String> entry : mapArgs.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
            FormBody body = builder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = shortHttpClient.newCall(request).execute();

            if (response != null && response.isSuccessful()) {
                return StringUtil.string2object(response.body().string(), tC);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Okhttp3POST请求异常", e);
        }
    }

    /**
     * Post请求
     *
     * @param url      请求地址 http://ip:port/context-path/request-mapping.json
     * @param jsonArgs json字符串格式的参数 {"username":typa1@qq.com,"password":123456}
     * @return String 返回结果原始字符串
     * @throws RuntimeException 运行时异常
     */
    public String request(String url, String jsonArgs) {
        try {
            RequestBody body = RequestBody.create(JSON, jsonArgs);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = shortHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Okhttp3POST请求异常", e);
        }
    }
    
    /**
     * Post请求
     *
     * @param url           请求地址 http://ip:port/context-path/request-mapping.json
     * @param args          参数字符串，json字符串格式的参数 {"username":typa1@qq.com,"password":123456} 
     * 						或xml格式的字符串参数 <beans><bean><name>qinH</name><age>18</age></bean></beans>
     * @param contentType   参数类型：可以是json或者xml格式
     * @return String       返回结果原始字符串
     * @throws RuntimeException 运行时异常
     */
    public String request(String url, String args, MediaType contentType) {
        try {
            RequestBody body = RequestBody.create(contentType, args);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = shortHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Okhttp3POST请求异常", e);
        }
    }

    /***
     * Post请求
     * @param url 请求地址 http://ip:port/context-path/request-mapping.json
     * @param mapArgs map字符串键值对封装的请求参数
     * @return String 返回结果原始字符串
     * @throws RuntimeException 运行时异常
     */
    public String request(String url, Map<String, String> mapArgs) {
        try {

            FormBody.Builder builder = new FormBody.Builder();
            if (mapArgs != null) {
                for (Map.Entry<String, String> entry : mapArgs.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
            FormBody body = builder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = shortHttpClient.newCall(request).execute();

            if (response != null && response.isSuccessful()) {
                return response.body().string();
            } else {
                return "";
            }
        } catch (IOException e) {
            throw new RuntimeException("Okhttp3POST请求异常", e);
        }
    }

    @Override
    public <T> T request(String url, Class<T> tC) {
        return null;
    }

    @Override
    public String request(String url) {
        return null;
    }
}
