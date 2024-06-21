package com.zjx.courese.authserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zjx.courese.authserver.dao.SearchRecordsMapper;
import com.zjx.courese.authserver.service.ISearchRecordsService;
import org.springframework.stereotype.Service;
import com.zjx.courese.authserver.entity.SearchRecords;

@Service
public class SearchRecordsServiceImpl extends ServiceImpl<SearchRecordsMapper, SearchRecords> implements ISearchRecordsService {

}
