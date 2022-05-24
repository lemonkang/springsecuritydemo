package com.example.springsecuritysecond.filter;

import com.example.springsecuritysecond.entity.custom.LoginUser;
import com.example.springsecuritysecond.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    //注入Redis对象
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("token");
        if (Objects.isNull(token)){
            //放行
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //1.对token进行解析获取用户ID
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("登录异常");
        }
        String id = claims.getSubject();
        //根据ID从Redis中获取UserDetails的信息
        LoginUser loginUser = (LoginUser)redisTemplate.opsForValue().get(id);
        if (Objects.isNull(loginUser)){
            throw new RuntimeException("登录异常");
        }
        //把LoginUser存入到securitycontect中
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
