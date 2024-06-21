package com.zjx.courese.authserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-06 21:30:46
 */
@Data
@TableName("users")
public class UsersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String contactInfo;
	/**
	 * 
	 */
	private String realIdentity;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private String content;

}
