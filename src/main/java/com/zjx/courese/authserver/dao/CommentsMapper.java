package com.zjx.courese.authserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.courese.authserver.entity.Comments;
import com.zjx.courese.authserver.entity.vo.CommentsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {
    List<CommentsVO> queryComments(Page<CommentsVO> page, @Param("videoId") String videoId);
}
