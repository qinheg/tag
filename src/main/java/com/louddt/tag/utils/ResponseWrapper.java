package com.louddt.tag.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ResponseWrapper<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    //200-成功
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;
    //1001-提示信息，如参数不能为空等
    public static final int CODE_WARNING = 1001;
    //1002-用户未登录或过期
    public static final int CODE_NOT_LOGIN = 1002;
    //1003-用户无操作权限
    public static final int CODE_NO_AUTHORITY = 1003;

    @JsonProperty(value = "code")
    public int code;

    @JsonProperty(value = "msg")
    public String message;

    @JsonProperty(value = "data")
    public T data;

    public ResponseWrapper() {

    }


    public ResponseWrapper(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


}
