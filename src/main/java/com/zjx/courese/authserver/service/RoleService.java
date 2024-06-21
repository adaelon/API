package com.zjx.courese.authserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.courese.authserver.entity.RoleEntity;
import com.zjx.courese.authserver.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-06 21:30:46
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

