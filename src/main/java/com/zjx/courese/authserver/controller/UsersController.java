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

import com.zjx.courese.authserver.entity.UsersEntity;
import com.zjx.courese.authserver.service.UsersService;




/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-06 21:30:46
 */
@RestController
@RequestMapping("")
public class UsersController {
    @Autowired
    private UsersService usersService;

    /**
     * 列表
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/admin/users/list")
    //@RequiresPermissions("authserver:users:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usersService.queryPage(params);

        return R.ok().put("page", page);
    }




}
