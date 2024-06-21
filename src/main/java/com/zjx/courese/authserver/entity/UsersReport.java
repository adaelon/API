package com.zjx.courese.authserver.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UsersReport对象", description="举报用户表")
public class UsersReport implements Serializable {

private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "被举报用户id")
    @TableField("deal_user_id")
    private String dealUserId;

    @TableField("deal_video_id")
    private String dealVideoId;

    @ApiModelProperty(value = "类型标题，让用户选择，详情见 枚举")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "举报人的id")
    @TableField("userid")
    private String userid;

    @ApiModelProperty(value = "举报时间")
    @TableField("create_date")
    private LocalDateTime createDate;


}
