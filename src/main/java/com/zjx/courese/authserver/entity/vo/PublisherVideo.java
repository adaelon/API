package com.zjx.courese.authserver.entity.vo;

import lombok.Data;

@Data
public class PublisherVideo {
    private UsersVO publisher;
    private boolean userLikeVideo;
}
