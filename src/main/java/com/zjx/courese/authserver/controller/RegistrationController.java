package com.zjx.courese.authserver.controller;

import com.zjx.courese.authserver.service.RegistrationService;
import com.zjx.courese.authserver.utils.PageUtils;
import com.zjx.courese.authserver.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    /**
     * 注册
     */
    @RequestMapping("/register")
    //@RequiresPermissions("authserver:permissions:list")
    public R list(@RequestParam Map<String, Object> params){
       boolean res= registrationService.registerUser(params);

       if(res==true){
           return R.ok().put("result","注册成功");
       }else{
           return R.error().put("result","注册失败");
       }

    }
}
