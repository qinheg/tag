package com.louddt.tag.okhttp;

import okhttp3.MediaType;

import java.util.Map;

public interface OkHttp3Utils {

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    MediaType XML  = MediaType.parse("application/xml; charset=utf-8");
    MediaType FORM = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");

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
    <T> T request(String url, String jsonArgs, Class<T> tC);

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
    <T> T request(String url, Map<String, String> mapArgs, Class<T> tC);

    /**
     * Post请求
     *
     * @param url      请求地址 http://ip:port/context-path/request-mapping.json
     * @param jsonArgs json字符串格式的参数 {"username":typa1@qq.com,"password":123456}
     * @return String 返回结果原始字符串
     * @throws RuntimeException 运行时异常
     */
    String request(String url, String jsonArgs);
    
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
    String request(String url, String args, MediaType contentType);

    /***
     * Post请求
     * @param url 请求地址 http://ip:port/context-path/request-mapping.json
     * @param mapArgs map字符串键值对封装的请求参数
     * @return String 返回结果原始字符串
     * @throws RuntimeException 运行时异常
     */
    String request(String url, Map<String, String> mapArgs);

    /**
     * Get请求
     *
     * @param url 请求地址 http://ip:port/context-path/request-mapping.json?args1=1&args2=2
     * @param tC  泛型类型
     * @param tC  泛型类型
     * @param <T> 泛型标识
     * @return T 转换后的泛型类型对象
     * @throws RuntimeException 运行时异常
     */
    <T> T request(String url, Class<T> tC);

    /**
     * Get请求
     *
     * @param url get请求地址 http://ip:port/context-path/request-mapping.json?args1=1&args2=2
     * @return String  返回结果原始字符串
     * @throws RuntimeException 运行时异常
     */
    String request(String url);
}
