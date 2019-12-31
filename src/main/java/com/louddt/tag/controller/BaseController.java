package com.louddt.tag.controller;


import com.alibaba.fastjson.JSON;
import com.louddt.tag.filter.LoginContext;
import com.louddt.tag.utils.ResponseWrapper;
import com.louddt.tag.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础控制器抽象类
 * 
 * <ul>
 * <li>统一异常处理
 * <li>统一数据返回格式
 * </ul>
 */
@Slf4j
public abstract class BaseController {

	public static Map<String,String> tokens = new HashMap<String,String>();
	public boolean debug = true;

	/**
	 * 创建成功时返回结果对象
	 *
	 * @param data 没有值 msg起提示作用
	 * @return ApiResponse<T>
	 */
	protected <T> ResponseWrapper<T> success(String msg, T data) {
		return new ResponseWrapper<>(ResponseWrapper.CODE_SUCCESS, msg, data);
	}

	/**
	 * 错误消息及具体的数据
	 * @param message
	 * @return ApiResponse<T>
	 */
	protected  <T> ResponseWrapper<T> error(String message) {
		return new ResponseWrapper<>(ResponseWrapper.CODE_ERROR, message, null);
	}

	/**
	 * 警告或提示信息
	 * @param code
	 * @param message
	 * @return ApiResponse<T>
	 */
	protected  <T> ResponseWrapper<T> warning(int code ,String message) {
		return new ResponseWrapper<>(code, message, null);
	}

	public String getUserId(){
		return LoginContext.getUserId();
	}
}
