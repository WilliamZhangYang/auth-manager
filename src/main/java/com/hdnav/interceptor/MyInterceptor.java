package com.hdnav.interceptor;

import com.hdnav.util.TokenUtil;
import io.netty.util.internal.StringUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class MyInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断token是否存在
        if (StringUtil.isNullOrEmpty(request.getHeader("token"))){
            logger.info("token为空！");
            return false;
        }
        //对token解码
        String token = Base64.decodeBase64(request.getHeader("token").getBytes()).toString();
        logger.info("token: "+token);

        //判断timestamp是否存在
        if (StringUtil.isNullOrEmpty(request.getHeader("timestamp"))){
            logger.info("timestamp为空！");
            return false;
        }
        String timestamp = Base64.decodeBase64(request.getHeader("timestamp").getBytes()).toString();
        logger.info("timestamp: "+timestamp);

        //判断signature是否存在
        if (StringUtil.isNullOrEmpty(request.getHeader("signature"))){
            logger.info("signature为空！");
            return false;
        }
        String signature = Base64.decodeBase64(request.getHeader("signature").getBytes()).toString();
        logger.info("signature: "+signature);

        //权限认证
        boolean checkToken = tokenUtil.verifyAuth(token,timestamp,signature);

        return checkToken;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
