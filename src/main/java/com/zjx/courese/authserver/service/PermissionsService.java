package com.zjx.courese.authserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.courese.authserver.entity.PermissionsEntity;
import com.zjx.courese.authserver.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-06 21:30:46
 */
public interface PermissionsService extends IService<PermissionsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PermissionsEntity getByUserId(Integer id);


    boolean changeRoleByUserId(Map<String, Object> params);
}

