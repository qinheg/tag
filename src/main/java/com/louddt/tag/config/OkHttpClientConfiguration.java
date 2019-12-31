package com.louddt.tag.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Data
@Slf4j
@Configuration
@EnableConfigurationProperties(OkHttpClientProperties.class)
public class OkHttpClientConfiguration {

    @Autowired
    private OkHttpClientProperties okHttpClientProperties;

    /**
     * 长连接的http连接器
     */
    @Bean(name = "longHttpClient")
    public OkHttpClient getLongHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(okHttpClientProperties.getLongConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(okHttpClientProperties.getLongReadTimeout(), TimeUnit.SECONDS).build();
        //okHttpClient.newBuilder().readTimeout(okHttpClientProperties.getLongReadTimeout(), TimeUnit.SECONDS);
        //连接池最大数，超时时间，超时单位
        ConnectionPool connectionPool = new ConnectionPool(okHttpClientProperties.getMaxIdleConnections(), okHttpClientProperties.getKeepAliveDurationNs(), TimeUnit.SECONDS);
        okHttpClient.newBuilder().connectionPool(connectionPool);
        log.info("\n *** Initialize okhttp3 client successful.");
        return okHttpClient;
    }

    /**
     * 短连接的http连接器
     */
    @Bean(name = "shortHttpClient")
    public OkHttpClient getShortHttpClient() {
        //连接池最大数，超时时间，超时单位
        ConnectionPool connectionPool = new ConnectionPool(okHttpClientProperties.getMaxIdleConnections(), okHttpClientProperties.getKeepAliveDurationNs(), TimeUnit.SECONDS);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(okHttpClientProperties.getShortConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(okHttpClientProperties.getShortReadTimeout(), TimeUnit.SECONDS)
                .connectionPool(connectionPool).build();
        return okHttpClient;
    }
}
