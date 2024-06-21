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

import com.zjx.courese.authserver.entity.PermissionsEntity;
import com.zjx.courese.authserver.service.PermissionsService;





@RestController
@RequestMapping("")
public class PermissionsController {
    @Autowired
    private PermissionsService permissionsService;

    /**
     * 列表
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/admin/permission/list")
    //@RequiresPermissions("authserver:permissions:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = permissionsService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/admin/permission/role")
    //@RequiresPermissions("authserver:permissions:list")
    public R changeRole(@RequestParam Map<String, Object> params){
        boolean res = permissionsService.changeRoleByUserId(params);

        return R.ok().put("res", res);
    }



}
