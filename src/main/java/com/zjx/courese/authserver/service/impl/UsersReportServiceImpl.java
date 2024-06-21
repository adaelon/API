package com.zjx.courese.authserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zjx.courese.authserver.dao.UsersReportMapper;
import com.zjx.courese.authserver.service.IUsersReportService;
import org.springframework.stereotype.Service;
import com.zjx.courese.authserver.entity.UsersReport;

@Service
public class UsersReportServiceImpl extends ServiceImpl<UsersReportMapper, UsersReport> implements IUsersReportService {

}
