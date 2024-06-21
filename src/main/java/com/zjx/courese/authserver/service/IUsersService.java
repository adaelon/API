package com.zjx.courese.authserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.courese.authserver.entity.Users;
import com.zjx.courese.authserver.entity.UsersReport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 *  服务类
 * </p>
 *
 
 */
public interface IUsersService extends IService<Users> {

    /**
     * 根据用户名判断是否存在
     * @param username
     * @return
     */
     boolean queryUserIsExist(String username);

    /**
     * 用户登录：根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return
     */
     Users queryUserForLogin(String username,String password) throws Exception;

    /**
     * @Description: 查询用户是否喜欢点赞视频
     */
    public boolean isUserLikeVideo(String userId, String videoId);

    /**
     * @Description: 增加用户和粉丝的关系
     */
    public void saveUserFanRelation(String userId, String fanId);

    /**
     * @Description: 删除用户和粉丝的关系
     */
    public void deleteUserFanRelation(String userId, String fanId);

    /**
     * @Description: 查询用户是否关注
     */
    public boolean queryIfFollow(String userId, String fanId);

    /**
     * @Description: 举报用户
     */
    public void reportUser(UsersReport userReport);


}
