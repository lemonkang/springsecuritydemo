package com.example.springsecuritysecond.entity.custom;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.springsecuritysecond.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor

public class LoginUser implements UserDetails {
    private SysUser sysUser;
    //存储权限信息
    private List<String> permissions;

    public LoginUser(SysUser sysUser, List<String> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }
    //存储SpringSecurity所需要的权限信息的集合
    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> authorityList;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorityList!=null){
            return authorityList;
        }
        authorityList = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return authorityList;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
