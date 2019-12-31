package com.louddt.tag.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("okHttpClientArgs")
@ConfigurationProperties(prefix = "okhttpclient")
public class OkHttpClientArgs {

    private Integer shortConnectTimeout = 120;
    private Integer shortReadTimeout    = 120;
    private Integer longConnectTimeout  = 240;
    private Integer longReadTimeout     = 240;

    private Integer maxIdleConnections  = 20;
    private Integer keepAliveDurationNs = 10;

    public Integer getShortConnectTimeout() {
        return shortConnectTimeout;
    }

    public void setShortConnectTimeout(Integer shortConnectTimeout) {
        this.shortConnectTimeout = shortConnectTimeout;
    }

    public Integer getShortReadTimeout() {
        return shortReadTimeout;
    }

    public void setShortReadTimeout(Integer shortReadTimeout) {
        this.shortReadTimeout = shortReadTimeout;
    }

    public Integer getLongConnectTimeout() {
        return longConnectTimeout;
    }

    public void setLongConnectTimeout(Integer longConnectTimeout) {
        this.longConnectTimeout = longConnectTimeout;
    }

    public Integer getLongReadTimeout() {
        return longReadTimeout;
    }

    public void setLongReadTimeout(Integer longReadTimeout) {
        this.longReadTimeout = longReadTimeout;
    }

    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public void setMaxIdleConnections(Integer maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    public Integer getKeepAliveDurationNs() {
        return keepAliveDurationNs;
    }

    public void setKeepAliveDurationNs(Integer keepAliveDurationNs) {
        this.keepAliveDurationNs = keepAliveDurationNs;
    }
}
