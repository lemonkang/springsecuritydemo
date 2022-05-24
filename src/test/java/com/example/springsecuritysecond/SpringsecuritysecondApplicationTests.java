package com.example.springsecuritysecond;

import com.example.springsecuritysecond.entity.SysUser;
import com.example.springsecuritysecond.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringsecuritysecondApplicationTests {
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;
    @Autowired(required = false)
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        System.out.println(sysUsers);
    }
    @Test
    public void encript(){
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);
    }
    @Test
    public void decript(){
        boolean matches = passwordEncoder.matches("123", "$2a$10$K58KmM.OVpzcQY0jco5kcuzoMntYpe0DEDsGd0YF.jWNI/XypBO1K");
        System.out.println(matches);
    }

}
