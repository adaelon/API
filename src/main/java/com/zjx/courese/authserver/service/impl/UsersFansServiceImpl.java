package com.zjx.courese.authserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zjx.courese.authserver.dao.UsersFansMapper;
import com.zjx.courese.authserver.service.IUsersFansService;
import org.springframework.stereotype.Service;
import com.zjx.courese.authserver.entity.UsersFans;

@Service
public class UsersFansServiceImpl extends ServiceImpl<UsersFansMapper, UsersFans> implements IUsersFansService {

}
