package com.dhlee.jpa.rest.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<GzipFilter> gzipFilter(){
        FilterRegistrationBean<GzipFilter> registrationBean = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(new GzipFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1); // 필터 순서 설정
        
        return registrationBean;
    }
    
//    @Bean
//    public FilterRegistrationBean<LoggingFilter> loggingFilter(){
//        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
//        
//        registrationBean.setFilter(new LoggingFilter());
//        registrationBean.addUrlPatterns("/api/*");
//        registrationBean.setOrder(2); // 필터 순서 설정
//        
//        return registrationBean;
//    }
}
