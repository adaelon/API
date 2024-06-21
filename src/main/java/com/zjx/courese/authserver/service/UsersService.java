package com.zjx.courese.authserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.courese.authserver.dto.UserDTO;
import com.zjx.courese.authserver.entity.UsersEntity;
import com.zjx.courese.authserver.utils.PageUtils;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-06 21:30:46
 */
public interface UsersService extends IService<UsersEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserDTO getByName(String username);

    CompletableFuture<UsersEntity> saveUserAsync(UsersEntity user);
}

