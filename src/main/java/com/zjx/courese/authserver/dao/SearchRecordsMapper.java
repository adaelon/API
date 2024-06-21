package com.zjx.courese.authserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjx.courese.authserver.entity.SearchRecords;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;


@Mapper
public interface SearchRecordsMapper extends BaseMapper<SearchRecords> {
    List<String> getHotWords();
}
