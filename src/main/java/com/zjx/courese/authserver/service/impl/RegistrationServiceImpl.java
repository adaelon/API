package com.zjx.courese.authserver.service.impl;

import com.zjx.courese.authserver.entity.PermissionsEntity;
import com.zjx.courese.authserver.entity.UsersEntity;
import com.zjx.courese.authserver.service.PermissionsService;
import com.zjx.courese.authserver.service.RegistrationService;
import com.zjx.courese.authserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PermissionsService permissionsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean registerUser(Map<String, Object> params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        int roleId = 2;//普通用户注册
        String contactInfo = (String) params.get("contactInfo");
        String realIdentity = (String) params.get("realIdentity");
        // 检查用户是否已经存在
        if (usersService.getByName(username) != null) {
            return false; // 用户已存在，注册失败
        }

        // 创建新用户
        UsersEntity newUser = new UsersEntity();
        newUser.setUsername(username);
        // 对密码进行加密
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encryptedPassword);
        newUser.setContactInfo(contactInfo);
        newUser.setRealIdentity(realIdentity);




        // 设置用户属性

        // 调用异步保存方法，并获取 CompletableFuture 对象
        CompletableFuture<UsersEntity> future = usersService.saveUserAsync(newUser);

        AtomicBoolean res = new AtomicBoolean(true);
        // 当 CompletableFuture 完成时执行操作
        future.thenAcceptAsync(savedUser -> {
            // 处理查询到的用户对象
            if (savedUser != null) {
                // 查询成功，执行后续操作
                PermissionsEntity newPermission = new PermissionsEntity();
                System.out.println(savedUser.getId());
                newPermission.setUserId(savedUser.getId());
                newPermission.setRoleId(roleId);
                newPermission.setUsername(savedUser.getUsername());
                permissionsService.save(newPermission);

                System.out.println("success");
                res.set(true);
            } else {
                // 查询失败，处理异常情况

               res.set(false);
            }
        });
        System.out.println(res.get());
        return res.get();




    }
}
