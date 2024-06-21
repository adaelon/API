package com.zjx.courese.authserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.courese.authserver.entity.ContentEntity;
import com.zjx.courese.authserver.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-07 09:30:37
 */
public interface ContentService extends IService<ContentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByUserId(Map<String, Object> params);
}

