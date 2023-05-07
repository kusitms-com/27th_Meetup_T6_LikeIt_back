package com.kusitms.hotsixServer.global.config.jwt;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    private final RedisDao redisDao;

    public JwtSecurityConfig(TokenProvider tokenProvider, RedisDao redisDao) throws IOException {
        this.tokenProvider = tokenProvider;
        this.redisDao = redisDao;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider,redisDao);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}