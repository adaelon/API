package com.zjx.courese.authserver.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.zjx.courese.authserver.utils.PageUtils;
import com.zjx.courese.authserver.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjx.courese.authserver.entity.ContentEntity;
import com.zjx.courese.authserver.service.ContentService;




@RestController
@RequestMapping("")
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 列表
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/admin/content/list")
    //@RequiresPermissions("authserver:content:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = contentService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/user/content/list")
    //@RequiresPermissions("authserver:content:list")
    public R listByUserId(@RequestParam Map<String, Object> params){
        PageUtils page = contentService.queryPageByUserId(params);

        return R.ok().put("page", page);
    }




}
