package com.louddt.tag.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class StringUtil {
    public static String trimAndFilterEmpty(String input){
        return Optional.ofNullable(input).map(String::trim).filter(s->!s.isEmpty()).orElse(null);
    }

    public static String objectToString(Object obj) {
        if (isEmptyOrNull(obj)) {
            return "";
        }
        return String.valueOf(obj).trim();
    }

    public static boolean isEmptyOrNull(Object obj) {
        if (obj == null) {
            return true;
        }
        String temp = String.valueOf(obj).trim();
        return isEmptyOrNull(temp);
    }

    public static boolean isEmptyOrNull(String source) {
        return (StringUtils.isEmpty(source)) || ("null".equalsIgnoreCase(source)) || ("undefined".equalsIgnoreCase(source));
    }

    public static boolean isNotEmptyOrNull(String source) {
        return !isEmptyOrNull(source);
    }

    public static final <T> T string2object(String json, Class<T> tC) {
        return JSON.parseObject(json, tC);
    }
}
