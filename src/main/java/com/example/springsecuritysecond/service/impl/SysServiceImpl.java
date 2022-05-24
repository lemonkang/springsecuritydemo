package com.example.springsecuritysecond.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springsecuritysecond.entity.SysUser;
import com.example.springsecuritysecond.mapper.SysUserMapper;
import com.example.springsecuritysecond.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
