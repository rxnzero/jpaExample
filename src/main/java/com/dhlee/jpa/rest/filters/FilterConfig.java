package com.dhlee.jpa.rest.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<GzipFilter> loggingFilter(){
        FilterRegistrationBean<GzipFilter> registrationBean = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(new GzipFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1); // 필터 순서 설정
        
        return registrationBean;
    }
}
