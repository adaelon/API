package com.zjx.courese.authserver.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Bgm implements Serializable {

private static final long serialVersionUID=1L;

    private String id;

    @TableField("author")
    private String author;

    @TableField("name")
    private String name;


    @TableField("path")
    private String path;


}
