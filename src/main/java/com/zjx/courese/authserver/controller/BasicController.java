package com.zjx.courese.authserver.controller;


import com.zjx.courese.authserver.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BasicController {
    @Autowired
    public RedisOperator redisOperator;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    @Value("${fileSpace}")
    protected String FILE_SPACE;

    // 每页分页的记录数
    public static final Integer PAGE_SIZE = 5;
}