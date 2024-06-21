package com.xiajun.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${fileSpace}")
    private String fileSpace;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String fileLocations = "file:" + fileSpace;
        if (!fileLocations.endsWith(File.separator)) {
            fileLocations += File.separator;
        }
        //资源映射, 自定义资源路径映射结尾必须加路径分割符！！！
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations(fileLocations);
    }




}
