package com.zjx.courese.authserver.service.impl;

import com.zjx.courese.authserver.utils.PageUtils;
import com.zjx.courese.authserver.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zjx.courese.authserver.dao.PermissionsDao;
import com.zjx.courese.authserver.entity.PermissionsEntity;
import com.zjx.courese.authserver.service.PermissionsService;


@Service("permissionsService")
public class PermissionsServiceImpl extends ServiceImpl<PermissionsDao, PermissionsEntity> implements PermissionsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PermissionsEntity> page = this.page(
                new Query<PermissionsEntity>().getPage(params),
                new QueryWrapper<PermissionsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PermissionsEntity getByUserId(Integer id) {
        PermissionsDao permissionsDao = this.baseMapper;
        PermissionsEntity permissionsEntity = permissionsDao.selectOne(new QueryWrapper<PermissionsEntity>().eq("user_id",id));
        return permissionsEntity;
    }
    @Override
    public boolean changeRoleByUserId(Map<String, Object> params) {
        Integer userId;

        try {

            userId = params.get("userId") instanceof Integer
                    ? (Integer) params.get("userId")
                    : Integer.parseInt((String) params.get("userId"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("courseId and userId must be valid integers", e);
        }
        // 根据用户ID查询用户信息
        PermissionsEntity permissionsEntity = this.getByUserId(userId);
        if (permissionsEntity == null) {
            // 用户不存在，返回失败
            return false;
        }

        // 获取当前用户的角色ID
        Integer roleId = permissionsEntity.getRoleId();
        // 根据当前角色ID判断要修改的目标角色ID
        Integer targetRoleId = (roleId == 1) ? 2 : 1;

        // 更新用户的角色ID为目标角色ID
        PermissionsDao permissionsDao = this.baseMapper;
        int rowsAffected = permissionsDao.updateRoleIdByUserId(userId, targetRoleId);

        // 判断更新是否成功
        if (rowsAffected > 0) {
            // 更新成功，返回 true
            return true;
        } else {
            // 更新失败，返回 false
            return false;
        }
    }

}