package com.pratik.springbootapp.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CommonsRequestLoggingFilter> requestLoggingFilter() {
        System.out.println("CommonsRequestLoggingFilter is being created!");  // sanity log
        CommonsRequestLoggingFilter commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
        commonsRequestLoggingFilter.setIncludeClientInfo(true);  // logs client IP, session id
        commonsRequestLoggingFilter.setIncludeQueryString(true); // logs URL query params
        commonsRequestLoggingFilter.setIncludePayload(true);    // logs request body (set true if you want, careful with large payloads)
        commonsRequestLoggingFilter.setIncludeHeaders(true);    // logs HTTP headers (false for simplicity)
        commonsRequestLoggingFilter.setAfterMessagePrefix("REQUEST DATA : ");
        commonsRequestLoggingFilter.setBeforeMessagePrefix("INCOMING REQUEST: ");
        commonsRequestLoggingFilter.setAfterMessagePrefix("REQUEST COMPLETE: ");


        FilterRegistrationBean<CommonsRequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(commonsRequestLoggingFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
