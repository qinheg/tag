package com.louddt.tag.filter;

import com.alibaba.fastjson.JSON;
import com.louddt.tag.controller.BaseController;
import com.louddt.tag.utils.BizStatusDefine;
import com.louddt.tag.utils.ResponseWrapper;
import com.louddt.tag.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(BizStatusDefine.TASK_LOGIN_TOKEN_CODE);
        log.info("--------------" + httpRequest.getRequestURL());
        log.info("--------------token : " + token);
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            log.info("--------------" + paramName + " : " + request.getParameter(paramName));
        }
        String userId = BaseController.tokens.get(token);
        String servletPath = httpRequest.getServletPath();
        try {
            if(servletPath.contains("/common/login") || servletPath.contains("/swagger-ui.html")||
                    servletPath.contains("/webjars/") || servletPath.contains("/images/") ||
                    servletPath.contains("/swagger-resources") || servletPath.contains("/configuration/") ||
                    servletPath.contains("/v2/") ||
                    servletPath.contains("/model/exportModel") ||
                    servletPath.contains("/task/exportJson")

            ){

            }else if(StringUtil.isEmptyOrNull(userId)){
                ResponseWrapper<String> res = new ResponseWrapper(ResponseWrapper.CODE_NOT_LOGIN,"未登录或登录已过期","");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().print(JSON.toJSONString(res));
                return;
            }else{
                LoginContext.setUserId(userId);
            }
            chain.doFilter(request, response);
        }finally {

        }
    }

    @Override
    public void destroy() {

    }
}
