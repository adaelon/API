package com.zjx.courese.authserver.service.impl;



import com.zjx.courese.authserver.bean.ComUser;
import com.zjx.courese.authserver.dto.UserDTO;
import com.zjx.courese.authserver.entity.UsersEntity;
import com.zjx.courese.authserver.service.UsersService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Resource
    private UsersService usersService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        /**
         * 1/通过userName 获取到userInfo信息
         * 2/通过User（UserDetails）返回details。
         */
        //通过userName获取用户信息
        UserDTO userDTO = usersService.getByName(userName);
        if(userDTO == null) {
            throw new UsernameNotFoundException("not found");
        }
        // 定义权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 根据角色值映射角色名称
        String roleName = null;
        if (userDTO.getRole()==1) {
            roleName = "ADMIN";
        } else if (userDTO.getRole()==2) {
            roleName = "USER";
        }

        // 如果角色名称不为空，则添加角色
        if (roleName != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
        }

        ComUser userDetails = new ComUser(userDTO.getUserId(), userDTO.getUsername(),userDTO.getPassword(),authorities);
        System.out.println(userDetails.getAuthorities());
        return userDetails;
    }
}
