package com.louddt.tag.okhttp;

import com.louddt.tag.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


@Slf4j
@Component("okHttp3GetUtils")
public class OkHttp3GetUtilsImpl implements OkHttp3Utils {

    @Autowired
    @Qualifier("shortHttpClient")
    private OkHttpClient shortHttpClient;

    /**
     * Get请求
     *
     * @param url 请求地址 http://ip:port/context-path/request-mapping.json?args1=1&args2=2
     * @param tC  泛型类型
     * @param <T> 泛型标识
     * @return T 转换后的泛型类型对象
     * @throws RuntimeException 运行时异常
     */
    public <T> T request(String url, Class<T> tC) {
        try {
            Request request = new Request.Builder().url(url).get().build();
            Response response = shortHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return StringUtil.string2object(response.body().string(), tC);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Okhttp3GET请求异常", e);
        }
    }

    /**
     * Get请求
     *
     * @param url get请求地址 http://ip:port/context-path/request-mapping.json?args1=1&args2=2
     * @return String  返回结果原始字符串
     * @throws RuntimeException 运行时异常
     */
    public String request(String url) {
        try {
            Request request = new Request.Builder().url(url).get().build();
            Response response = shortHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Okhttp3GET请求异常", e);
        }
    }

    @Override
    public <T> T request(String url, String jsonArgs, Class<T> tC) {
        return null;
    }

    @Override
    public <T> T request(String url, Map<String, String> mapArgs, Class<T> tC) {
        return null;
    }

    @Override
    public String request(String url, String jsonArgs) {
        return null;
    }
    
	@Override
	public String request(String url, String args, MediaType contentType) {
		return null;
	}
    
    @Override
    public String request(String url, Map<String, String> mapArgs) {
        return null;
    }
}
