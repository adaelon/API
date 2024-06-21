package com.zjx.courese.authserver.dao;

import com.zjx.courese.authserver.entity.PermissionsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-06 21:30:46
 */
@Mapper
public interface PermissionsDao extends BaseMapper<PermissionsEntity> {
    int updateRoleIdByUserId(@Param("user_id") Integer userId, @Param("role_id") Integer roleId);
}
