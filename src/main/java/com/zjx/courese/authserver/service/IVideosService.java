package com.zjx.courese.authserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.courese.authserver.entity.Comments;
import com.zjx.courese.authserver.entity.Videos;
import com.zjx.courese.authserver.entity.vo.CommentsVO;
import com.zjx.courese.authserver.entity.vo.VideosVO;
import com.zjx.courese.authserver.utils.PageUtils;


import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 视频信息表 服务类
 * </p>
 *
 
 */
public interface IVideosService extends IService<Videos> {

    //更新用户的点赞数

    Videos updateLikeCounts(String id, Long likeCounts);
    Optional<Videos> findVideoByIdAndDirection(String id, int direction);
    void addReturnedVideo(String id);
    boolean isVideoReturned(String id);
    void addRevisitableVideo(String id);
    boolean isVideoRevisitable(String id);

    /**
     * @Description: 分页查询视频列表
     */
    public Page<VideosVO> getAllVideos(Videos video, Integer isSaveRecord,
                                       Integer page, Integer pageSize);

    /**
     * @Description: 查询我喜欢的视频列表
     */
    public Page<VideosVO> queryMyLikeVideos(String userId, Integer page, Integer pageSize);


    boolean deleteVideo(String userId, String videoId);

    PageUtils queryMyVideo(Map<String, Object> params);

    /**
     * @Description: 查询我关注的人的视频列表
     */
    public Page<VideosVO> queryMyFollowVideos(String userId, Integer page, Integer pageSize);

    /**
     * @Description: 获取热搜词列表
     */
    public List<String> getHotwords();

    /**
     * @Description: 用户喜欢/点赞视频
     */
    public void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 用户不喜欢/取消点赞视频
     */
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 用户留言
     */
    public void saveComment(Comments comment);

    /**
     * @Description: 留言分页
     */
    public Page<CommentsVO> getAllComments(String videoId, Integer page, Integer pageSize);

}
