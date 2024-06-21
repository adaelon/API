package com.zjx.courese.authserver.controller;


import com.zjx.courese.authserver.controller.BasicController;
import com.zjx.courese.authserver.service.impl.BgmServiceImpl;
import com.zjx.courese.authserver.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value = "背景音乐业务接口",tags = {"背景音乐相关业务controller"})
@RequestMapping("/bgm")
public class BgmController extends BasicController {
    @Autowired
    private BgmServiceImpl bgmService;


    @ApiOperation(value = "获取背景音乐列表", notes = "获取背景音乐列表的接口")
    @PostMapping("/list")
    public JSONResult list(){
        return JSONResult.ok(bgmService.list());
    }
}