package com.zjx.courese.authserver.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UsersLikeVideos对象", description="用户喜欢的/赞过的视频")
public class UsersLikeVideos implements Serializable {

private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "用户")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "视频")
    @TableField("video_id")
    private String videoId;


}
