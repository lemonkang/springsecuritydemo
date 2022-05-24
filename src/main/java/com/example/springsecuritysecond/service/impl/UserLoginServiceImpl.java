package com.example.springsecuritysecond.service.impl;

import com.example.springsecuritysecond.config.CommonResult;
import com.example.springsecuritysecond.entity.SysUser;
import com.example.springsecuritysecond.entity.custom.LoginUser;
import com.example.springsecuritysecond.service.UserLoginService;
import com.example.springsecuritysecond.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    //注入Redis对象
    @Autowired
    private RedisTemplate redisTemplate;
    //引入AuthenticationManager接口的授权对象
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public CommonResult login(SysUser sysUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(),sysUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        System.out.println(authenticate);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //获取用户ID
        String id = loginUser.getSysUser().getId().toString();
        //生成token
        String jwt = JwtUtil.createJWT( id);
        //把用户信息存到Redis中
        redisTemplate.opsForValue().set(id,loginUser);
        return  CommonResult.success().data("token",jwt);
    }


    /**
     * 退出登录
     */
    @Override
    public CommonResult outlogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid = loginUser.getSysUser().getId().toString();
        Boolean delete = redisTemplate.delete(userid);
        if (delete) {
            return CommonResult.success();
        }
        return CommonResult.fail();


    }
}
