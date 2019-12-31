package com.louddt.tag.filter;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LoginContext {

    public static final String KET_USER_ID = "USER_ID";

    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return request;
    }

    public static <T> void set(String key, T value){
        HttpServletRequest request = getCurrentRequest();
        request.setAttribute(key, value);
    }

    public static <T> T get(String key){
        HttpServletRequest request = getCurrentRequest();
        return (T) request.getAttribute(key);
    }

    public static void setUserId(String userId) {
        set(KET_USER_ID, userId);
    }

    public static String getUserId() {
        return get(KET_USER_ID);
    }
}
