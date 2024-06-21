package com.zjx.courese.authserver.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zjx.courese.authserver.entity.Bgm;
import com.zjx.courese.authserver.entity.Comments;
import com.zjx.courese.authserver.entity.Videos;
import com.zjx.courese.authserver.entity.vo.CommentsVO;
import com.zjx.courese.authserver.entity.vo.VideosVO;
import com.zjx.courese.authserver.enums.ThumbnailTypeEnum;
import com.zjx.courese.authserver.enums.VideoStatusEnum;
import com.zjx.courese.authserver.service.impl.BgmServiceImpl;
import com.zjx.courese.authserver.service.impl.VideosServiceImpl;
import com.zjx.courese.authserver.utils.FFMpeg;
import com.zjx.courese.authserver.utils.JSONResult;
import com.zjx.courese.authserver.utils.PageUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import com.zjx.courese.authserver.utils.R;
@RestController
@Slf4j
@Api(value = "视频相关业务接口", tags = {"视频相关业务controller"})
@RequestMapping("video")
public class VideoController extends BasicController {
    @Autowired
    private BgmServiceImpl bgmService;
    @Autowired
    private VideosServiceImpl videosService;

    @PutMapping("/updateLikes")
    @ApiOperation("更新视频点赞数量")
    public Videos updateLikeCounts(@RequestParam String id, @RequestParam Long likeCounts) {
        System.out.println("运行到点赞了");
        return videosService.updateLikeCounts(id, likeCounts);
    }

    @GetMapping("/navigate")
    @ApiOperation("根据ID和方向查找视频")
    public Videos navigateVideo(@RequestParam String id, @RequestParam int direction) {
        return videosService.findVideoByIdAndDirection(id, direction)
                .orElseThrow(() -> new IllegalArgumentException("未找到相应的视频"));
    }

    @Value("${thumbnailType}")
    private String thumbnailType;
    @Value("${fileServer.url}")
    String fileUrl;
    @RequestMapping(value = "fileUpload",method = RequestMethod.POST)
    public R fileUpload(@RequestParam("file") MultipartFile file) throws IOException, MyException {
        String imgUrl=fileUrl;
        if(file!=null){
            System.out.println("multipartFile = " + file.getName()+"|"+file.getSize());


            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String filename=    file.getOriginalFilename();
            String extName = StringUtils.substringAfterLast(filename, ".");

            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
            imgUrl=fileUrl ;
            imgUrl+=":8189";

            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                imgUrl+="/"+path;
            }

            //System.out.println(imgUrl);

        }

        return R.ok().put("imageUrl",imgUrl);
    }
    @ApiOperation(value = "用户上传视频", notes = "用户上传视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form", dataTypeClass = String.class),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false,
                    dataType = "String", paramType = "form", dataTypeClass = String.class)
    })
    @PostMapping(value = "/upload")
    public JSONResult upload(String userId, String videoPath, String coverPath,
                             String desc) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }

        // 数据入库
        Videos video = new Videos();
        video
                .setVideoDesc(desc)
                .setCoverPath(videoPath)
                .setCoverPath(coverPath)
                .setStatus(VideoStatusEnum.SUCCESS.value)
                .setCreateTime(LocalDateTime.now());
        videosService.save(video);

        return JSONResult.ok(video.getId());
    }

    /**
     *
     * @Description: 分页和搜索查询视频列表
     * isSaveRecord：1 - 需要保存
     * 				 0 - 不需要保存 ，或者为空的时候
     */
    @PostMapping(value="/showAll")
    public JSONResult showAll(@RequestBody Videos video, Integer isSaveRecord,
                                   Integer page, Integer pageSize) throws Exception {

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        Page<VideosVO> allVideos = videosService.getAllVideos(video, isSaveRecord, page, pageSize);
        return JSONResult.ok(allVideos);
    }
    /**
     *
     * @Description: 分页和搜索查询我的视频列表
     *
     */
    @PostMapping(value="/showMyVideo")
    public R showMyVideo(
            @RequestParam Map<String, Object> params) throws Exception {


        PageUtils page = videosService.queryMyVideo(params);


        return R.ok().put("page", page);
    }
    /**
     * @Description: 我关注的人发的视频
     */
    @PostMapping("/showMyFollow")
    public JSONResult showMyFollow(String userId, Integer page) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.ok();
        }

        if (page == null) {
            page = 1;
        }

        int pageSize = 6;

        Page<VideosVO> videosVOPage = videosService.queryMyFollowVideos(userId, page, pageSize);

        return JSONResult.ok(videosVOPage);
    }

    /**
     * @Description: 我收藏(点赞)过的视频列表
     */
    @PostMapping("/showMyLike")
    public JSONResult showMyLike(String userId, Integer page, Integer pageSize) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.ok();
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 6;
        }
        Page<VideosVO> videosVOPage = videosService.queryMyLikeVideos(userId, page, pageSize);

        return JSONResult.ok(videosVOPage);
    }

    @PostMapping(value="/hot")
    public JSONResult hot() throws Exception {
        return JSONResult.ok(videosService.getHotwords());
    }

    @PostMapping(value="/userLike")
    public JSONResult userLike(String userId, String videoId, String videoCreaterId)
            throws Exception {
        videosService.userLikeVideo(userId, videoId, videoCreaterId);
        return JSONResult.ok();
    }

    @PostMapping(value="/userUnLike")
    public JSONResult userUnLike(String userId, String videoId, String videoCreaterId) throws Exception {
        videosService.userUnLikeVideo(userId, videoId, videoCreaterId);
        return JSONResult.ok();
    }

    @PostMapping("/saveComment")
    public JSONResult saveComment(@RequestBody Comments comment,
                                       String fatherCommentId, String toUserId) throws Exception {

        comment.setFatherCommentId(fatherCommentId);
        comment.setToUserId(toUserId);

        videosService.saveComment(comment);
        return JSONResult.ok();
    }

    @PostMapping("/getVideoComments")
    public JSONResult getVideoComments(String videoId, Integer page, Integer pageSize) throws Exception {

        if (StringUtils.isBlank(videoId)) {
            return JSONResult.ok();
        }

        // 分页查询视频列表，时间顺序倒序排序
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Page<CommentsVO> list = videosService.getAllComments(videoId, page, pageSize);

        return JSONResult.ok(list);
    }
    /**
     * 删除(权限管理，只能删除自己的视频)
     */
    @RequestMapping("/delete")

    public R delete(String videoId, String userId){
        videosService.deleteVideo(userId,videoId);

        return R.ok();
    }
}