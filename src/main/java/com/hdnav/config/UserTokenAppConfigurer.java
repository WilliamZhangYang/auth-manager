package com.hdnav.config;

import com.hdnav.interceptor.MyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class UserTokenAppConfigurer extends WebMvcConfigurationSupport {

    private static Logger logger = LoggerFactory.getLogger(UserTokenAppConfigurer.class);

    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        logger.info("进入UserTokenAppConfigurer.addInterceptors方法!");
        registry.addInterceptor(myInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/account/login","/account/register","/account/getToken");
        super.addInterceptors(registry);
    }
}
