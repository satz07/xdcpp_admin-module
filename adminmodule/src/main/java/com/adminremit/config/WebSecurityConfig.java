package com.adminremit.config;

import com.adminremit.auth.service.AuthService;
import com.adminremit.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.lang.reflect.Method;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthService authService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/product").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/product/list").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/partner").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/partner/list").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/designation").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/designation/list").access("hasRole('ROLE_SUPERADMIN')")
                //.antMatchers("/fileupload/**").access("hasRole('ROLE_SUPERADMIN')")
                //.antMatchers("/backoffice/**").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/staging/**").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/reconciliation/**").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/purpose/**").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/fileupload/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/api/web3/**").permitAll()
                .antMatchers("/backoffice/**").permitAll()
                .antMatchers("/product/complaince/complaincemaker").permitAll()
                .antMatchers("/complaince/**").permitAll()
                //.antMatchers("/backoffice/staging/**").permitAll()
                //.antMatchers("/backoffice/disbursment/**").permitAll()
                .antMatchers(
                        "/js/**",
                        "/images/**",
                        "../images/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**").permitAll()
                .antMatchers("/forgetpassword",
                        "/password",
                        "/reset/password/**",
                        "/create/password",
                        "/api/**",
                        "/password").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringAntMatchers("/api/web3/**")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/landing",true)
                .permitAll()
                .and()
                .rememberMe()
                .key("remitt-login")
                .rememberMeParameter("remember").userDetailsService(authService)
                .rememberMeCookieName("remitt-login")
                .tokenValiditySeconds(300)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(authService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
                "/js/**",
                "/images/**",
                "../images/**",
                "/css/**",
                "/img/**"
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
