package com.zjx.courese.authserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zjx.courese.authserver.dao.VideosMapper;
import com.zjx.courese.authserver.entity.*;
import com.zjx.courese.authserver.entity.vo.CommentsVO;
import com.zjx.courese.authserver.entity.vo.VideosVO;
import com.zjx.courese.authserver.service.IVideosService;
import com.zjx.courese.authserver.utils.PageUtils;
import com.zjx.courese.authserver.utils.TimeAgoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class VideosServiceImpl extends ServiceImpl<VideosMapper, Videos> implements IVideosService {
    @Autowired
    private SearchRecordsServiceImpl searchRecordsService;
    @Autowired
    private UsersLikeVideosServiceImpl usersLikeVideosService;
    @Autowired
    private UserServiceImpl usersService;
    @Autowired
    private CommentsServiceImpl commentsService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean save(Videos entity) {
        return super.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateById(Videos entity) {
        return super.updateById(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Page<VideosVO> getAllVideos(Videos video, Integer isSaveRecord,
                                       Integer page, Integer pageSize) {

        // 保存热搜词
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords record = new SearchRecords();
            record.setContent(desc);
            searchRecordsService.save(record);
        }
        Page<VideosVO> videosVOPage = new Page<>();
        videosVOPage.setCurrent(page).setSize(pageSize);
        List<VideosVO> list = this.getBaseMapper().queryAllVideos(videosVOPage, desc, userId);

        videosVOPage.setRecords(list);

        return videosVOPage;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Page<VideosVO> queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        Page<VideosVO> videosVOPage = new Page<>();
        videosVOPage.setCurrent(page).setSize(pageSize);
        List<VideosVO> list = this.getBaseMapper().queryMyLikeVideos(userId);
        videosVOPage.setRecords(list);

        return videosVOPage;
    }

    @Override
    public boolean deleteVideo(String userId, String videoId) {
        // 查找视频
        Videos video = this.getById(videoId);
        if (video == null) {
            return false;
        }

        // 验证视频是否属于当前用户
        if (!video.getUserId().equals(userId)) {
           return false;
        }

        // 删除视频
        this.removeById(videoId);
        return true;
    }
    @Override
    public PageUtils queryMyVideo(Map<String, Object> params) {
        String userId;
        Integer pageSum;
        Integer size;
        if(params.get("page")==null){
            pageSum=1;
        }else{
            pageSum = params.get("page") instanceof Integer
                    ? (Integer) params.get("page")
                    : Integer.parseInt((String) params.get("page"));
        }
        if(params.get("size")==null){
            size=10;
        }else {
            size = params.get("size") instanceof Integer
                    ? (Integer) params.get("size")
                    : Integer.parseInt((String) params.get("size"));
        }
        try {
            userId = (String) params.get("userId");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid userId", e);
        }

        Page<Videos> pageParam = new Page<>(pageSum, size);
        IPage<Videos> page = this.page(
                pageParam,
                new QueryWrapper<Videos>().eq("user_id", userId)
        );

        return new PageUtils(page);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Page<VideosVO> queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
        Page<VideosVO> videosVOPage = new Page<>();
        videosVOPage.setCurrent(page).setSize(pageSize);

        List<VideosVO> list = this.getBaseMapper().queryMyFollowVideos(userId);

        videosVOPage.setRecords(list);
        return videosVOPage;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotwords() {
        return searchRecordsService.getBaseMapper().getHotWords();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 1. 保存用户和视频的喜欢点赞关联关系表
        UsersLikeVideos ulv = new UsersLikeVideos();

        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        usersLikeVideosService.save(ulv);

        // 2. 视频喜欢数量累加
        this.getBaseMapper().addVideoLikeCount(videoId);

        // 3. 用户受喜欢数量的累加
        usersService.getBaseMapper().addReceiveLikeCount(videoCreaterId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 1. 删除用户和视频的喜欢点赞关联关系表
        final LambdaUpdateWrapper<UsersLikeVideos> wrapper = Wrappers.lambdaUpdate();

        wrapper.eq(UsersLikeVideos::getUserId, userId)
                .eq(UsersLikeVideos::getVideoId, videoId);


        usersLikeVideosService.remove(wrapper);

        // 2. 视频喜欢数量累减
        this.getBaseMapper().reduceVideoLikeCount(videoId);

        // 3. 用户受喜欢数量的累减
        usersService.getBaseMapper().reduceReceiveLikeCount(videoCreaterId);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {

        comment.setCreateTime(LocalDateTime.now());
        commentsService.save(comment);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Page<CommentsVO> getAllComments(String videoId, Integer page, Integer pageSize) {


        Page<CommentsVO> commentsVOPage = new Page<>();
        commentsVOPage.setCurrent(page);
        commentsVOPage.setSize(pageSize);

        List<CommentsVO> list = commentsService.getBaseMapper().queryComments(commentsVOPage, videoId);

        for (CommentsVO c : list) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
        }

        commentsVOPage.setRecords(list);


        return commentsVOPage;
    }
}
