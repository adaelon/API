package com.zjx.courese.authserver.dao;

import com.zjx.courese.authserver.entity.UsersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-06 21:30:46
 */
@Mapper
public interface UsersDao extends BaseMapper<UsersEntity> {
	
}
