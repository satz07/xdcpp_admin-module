package com.adminremit.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WEB3 migration changes - Configuration to register Web3ModeFilter
 */
@Configuration
public class Web3FilterConfig {

    @Bean
    public FilterRegistrationBean<Web3ModeFilter> web3ModeFilterRegistration(Web3ModeFilter filter) {
        FilterRegistrationBean<Web3ModeFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        registration.setName("web3ModeFilter");
        return registration;
    }
}

