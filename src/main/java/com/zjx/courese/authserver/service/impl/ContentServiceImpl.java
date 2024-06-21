package com.zjx.courese.authserver.service.impl;

import com.zjx.courese.authserver.utils.PageUtils;
import com.zjx.courese.authserver.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zjx.courese.authserver.dao.ContentDao;
import com.zjx.courese.authserver.entity.ContentEntity;
import com.zjx.courese.authserver.service.ContentService;


@Service("contentService")
public class ContentServiceImpl extends ServiceImpl<ContentDao, ContentEntity> implements ContentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ContentEntity> page = this.page(
                new Query<ContentEntity>().getPage(params),
                new QueryWrapper<ContentEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public PageUtils queryPageByUserId(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        IPage<ContentEntity> page = this.page(
                new Query<ContentEntity>().getPage(params),
                new QueryWrapper<ContentEntity>().eq("userId",userId)
        );

        return new PageUtils(page);
    }

}