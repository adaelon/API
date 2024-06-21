package com.zjx.courese.authserver.sec.handler;


import cn.hutool.json.JSONUtil;

import com.alibaba.fastjson.JSONObject;
import com.zjx.courese.authserver.bean.ComUser;
import com.zjx.courese.authserver.bean.Result;

import com.zjx.courese.authserver.service.UsersService;
import com.zjx.courese.authserver.utils.JwtUtils;
import com.zjx.courese.authserver.utils.RedisUtil;
import com.zjx.courese.authserver.utils.result.SystemResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;
    @Resource
    RedisUtil redisUtils;

    @Autowired
    UsersService usersService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ComUser user = (ComUser) authentication.getPrincipal();
        String jwt = JwtUtils.generateToken(user);
        if (redisUtils.exists("login:" + user.getUserId())) {
            redisUtils.remove("login:" + user.getUserId());
        }

        redisUtils.set("login:" + user.getUserId(), jwt);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        response.setHeader("Authorization", jwt);



        Result result = Result.succ(usersService.getByName(user.getUsername()));

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();

    }

}

