package com.zjx.courese.authserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zjx.courese.authserver.dao.CommentsMapper;
import com.zjx.courese.authserver.service.ICommentsService;
import org.springframework.stereotype.Service;
import com.zjx.courese.authserver.entity.Comments;

@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

}