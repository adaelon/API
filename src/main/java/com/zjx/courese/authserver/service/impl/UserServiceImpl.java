package com.zjx.courese.authserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zjx.courese.authserver.dao.UsersMapper;
import com.zjx.courese.authserver.entity.UsersFans;
import com.zjx.courese.authserver.entity.UsersLikeVideos;
import com.zjx.courese.authserver.entity.UsersReport;
import com.zjx.courese.authserver.service.IUsersFansService;
import com.zjx.courese.authserver.service.IUsersLikeVideosService;
import com.zjx.courese.authserver.service.IUsersReportService;
import com.zjx.courese.authserver.service.IUsersService;
import com.zjx.courese.authserver.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.zjx.courese.authserver.entity.Users;

@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Autowired
    private IUsersLikeVideosService usersLikeVideosService;
    @Autowired
    private IUsersFansService usersFansService;
    @Autowired
    private IUsersReportService usersReportService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserIsExist(String username) {
        final LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, username);
        return Optional.ofNullable(getOne(wrapper)).isPresent();
    }

    @Override
    public Users queryUserForLogin(String username, String password) throws Exception {
        final LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, username)
                .eq(Users::getPassword, MD5Utils.getMD5Str(password));
        return getOne(wrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean save(Users entity) {
        return super.save(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users getById(Serializable id) {
        return super.getById(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return false;
        }
        final LambdaQueryWrapper<UsersLikeVideos> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UsersLikeVideos::getUserId, userId)
                .eq(UsersLikeVideos::getVideoId, videoId);
        List<UsersLikeVideos> list = usersLikeVideosService.list(wrapper);
        return CollectionUtils.isNotEmpty(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserFanRelation(String userId, String fanId) {
        UsersFans userFan = new UsersFans();
        userFan.setUserId(userId);
        userFan.setFanId(fanId);

        usersFansService.save(userFan);

        this.getBaseMapper().addFansCount(userId);
        this.getBaseMapper().addFollersCount(fanId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserFanRelation(String userId, String fanId) {
        final LambdaUpdateWrapper<UsersFans> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(UsersFans::getUserId, userId)
                .eq(UsersFans::getFanId, fanId);
        //删除关系
        usersFansService.remove(wrapper);
        this.getBaseMapper().reduceFansCount(userId);
        this.getBaseMapper().reduceFollersCount(fanId);

    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {
        final LambdaQueryWrapper<UsersFans> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UsersFans::getUserId, userId).eq(UsersFans::getFanId, fanId);
        List<UsersFans> list = usersFansService.list(wrapper);
        return CollectionUtils.isNotEmpty(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void reportUser(UsersReport userReport) {

        userReport.setCreateDate(LocalDateTime.now());
        usersReportService.save(userReport);
    }
}

