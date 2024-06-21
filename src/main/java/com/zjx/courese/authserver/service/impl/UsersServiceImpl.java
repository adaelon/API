package com.zjx.courese.authserver.service.impl;

import com.zjx.courese.authserver.dto.UserDTO;
import com.zjx.courese.authserver.entity.PermissionsEntity;
import com.zjx.courese.authserver.service.PermissionsService;
import com.zjx.courese.authserver.utils.PageUtils;
import com.zjx.courese.authserver.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zjx.courese.authserver.dao.UsersDao;
import com.zjx.courese.authserver.entity.UsersEntity;
import com.zjx.courese.authserver.service.UsersService;


@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Autowired
    private PermissionsService permissionsService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsersEntity> page = this.page(
                new Query<UsersEntity>().getPage(params),
                new QueryWrapper<UsersEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public UserDTO getByName(String username) {
        UsersDao usersDao = this.baseMapper;
        UsersEntity usersEntity = usersDao.selectOne(new QueryWrapper<UsersEntity>().eq("username",username));
        if(usersEntity==null){
            return  null;
        }else {
            PermissionsEntity permissionsEntity=permissionsService.getByUserId(usersEntity.getId());
            UserDTO userDTO= new UserDTO();
            userDTO.setUserId(usersEntity.getId());
            userDTO.setUsername(usersEntity.getUsername());
            userDTO.setPassword(usersEntity.getPassword());
            userDTO.setRole(permissionsEntity.getRoleId());
            userDTO.setContactInfo(usersEntity.getContactInfo());
            userDTO.setRealIdentity(usersEntity.getRealIdentity());
            return userDTO;
        }


    }
    //注册时，保存用户和permission表对应关系
    @Override
    public CompletableFuture<UsersEntity> saveUserAsync(UsersEntity user) {
        System.out.println("in");
        return CompletableFuture.supplyAsync(() -> {

            // 执行插入操作
            UsersDao usersDao = this.baseMapper;
            int num=usersDao.insert(user);

            // 返回用户对象
            return user;
        }).thenApplyAsync(insertedUser -> {

            // 执行查询操作
            UsersDao usersDao = this.baseMapper;
            return usersDao.selectOne(new QueryWrapper<UsersEntity>().eq("username", insertedUser.getUsername()));
        });
    }
}