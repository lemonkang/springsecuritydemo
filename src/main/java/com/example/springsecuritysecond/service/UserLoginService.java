package com.example.springsecuritysecond.service;

import com.example.springsecuritysecond.config.CommonResult;
import com.example.springsecuritysecond.entity.SysUser;

public interface UserLoginService {
    CommonResult login(SysUser sysUser);

    CommonResult outlogin();
}
