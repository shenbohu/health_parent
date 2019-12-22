package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.daomin.Permission;
import com.itheima.daomin.Role;
import com.itheima.daomin.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
//@component （把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
//@Component,@Service,@Controller,@Repository注解的类，并把这些类纳入进spring容器中管理。
public class SpringSecurityUserService implements UserDetailsService {
        // 根据数据库查询信息
    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user==null) {
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles(); // 获取用户的所有角色
        for (Role role : roles) {
            // 为用户授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();// 用户的所有权限
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));// 为用户授予权限
            }
        }
        org.springframework.security.core.userdetails.User securityuser =
                new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return  securityuser;

    }
}
