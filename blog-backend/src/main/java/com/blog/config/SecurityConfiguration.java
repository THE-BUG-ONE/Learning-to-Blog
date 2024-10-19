package com.blog.config;

import com.blog.entity.vo.Result;
import com.blog.filter.JwtAuthorizeFilter;
import com.blog.service.impl.UserDetailsServiceImpl;
import com.blog.utils.WebUtils;
import com.blog.utils.enums.AppHttpCodeEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
public class SecurityConfiguration {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private JwtAuthorizeFilter jwtAuthorizeFilter;

    @Resource
    private WebUtils webUtils;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception {
        return httpSecurity
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/login").anonymous()
                        .requestMatchers("/register").anonymous()
                        .requestMatchers("/logout").authenticated()
                        .requestMatchers("/admin/**").authenticated()
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().permitAll())
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(conf -> conf
                        .accessDeniedHandler(this::handle)          //授权失败处理器
                        .authenticationEntryPoint(this::commence))  //认证失败处理器
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        webUtils.renderString(response, Result.failure(AppHttpCodeEnum.UNAUTHORIZED));
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        webUtils.renderString(response, Result.failure());
    }
}
