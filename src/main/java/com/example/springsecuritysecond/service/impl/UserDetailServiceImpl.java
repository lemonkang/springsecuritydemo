package com.example.springsecuritysecond.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springsecuritysecond.entity.SysUser;
import com.example.springsecuritysecond.entity.custom.LoginUser;
import com.example.springsecuritysecond.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据username到数据库中查询user信息，如果没有查到用户信息返回一个错误，如果有用户信息就封装到userDetail
        //中返回
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUserName,s);
        SysUser sysUser = sysUserMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(sysUser)) {
            throw  new RuntimeException("用户不存在");
        }
        //TODO 根据用户查询权限信息 添加到LoginUser中
        List<String> list = new ArrayList<>(Arrays.asList("test"));
        return new LoginUser(sysUser,list);
    }
}
