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
@ApiModel(value="Comments对象", description="课程评论表")
public class Comments implements Serializable {

private static final long serialVersionUID=1L;

    private String id;

    @TableField("father_comment_id")
    private String fatherCommentId;

    @TableField("to_user_id")
    private String toUserId;

    @ApiModelProperty(value = "视频id")
    @TableField("video_id")
    private String videoId;

    @ApiModelProperty(value = "留言者，评论的用户id")
    @TableField("from_user_id")
    private String fromUserId;

    @ApiModelProperty(value = "评论内容")
    @TableField("comment")
    private String comment;

    @TableField("create_time")
    private LocalDateTime createTime;


}
