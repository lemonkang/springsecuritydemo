package com.example.springsecuritysecond.controller;

import com.example.springsecuritysecond.config.CommonResult;
import com.example.springsecuritysecond.entity.SysUser;
import com.example.springsecuritysecond.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private UserLoginService userLoginService;
    @PostMapping("/login")
    public CommonResult inteceptor( @RequestBody(required = false) SysUser sysUser){
        return userLoginService.login(sysUser);
    }
    @PostMapping("/otherurl")
    @PreAuthorize("hasAuthority('test')")
    public CommonResult otherurl( ){
        return CommonResult.success();
    }
    @PostMapping("/outlogin")
    public CommonResult outlogin(){

        return   userLoginService.outlogin();
    }

}
