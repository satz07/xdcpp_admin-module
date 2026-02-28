package com.adminremit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WEB3 migration changes - Configuration to register Web3ModeInterceptor
 * This ensures Web3 mode is set for ALL controller methods including REST endpoints
 */
@Configuration
public class Web3InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private Web3ModeInterceptor web3ModeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(web3ModeInterceptor)
                .addPathPatterns("/**") // Apply to all paths
                .excludePathPatterns(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/img/**",
                    "/libs/**",
                    "/webjars/**",
                    "/error",
                    "/favicon.ico"
                ); // Exclude static resources
    }
}
