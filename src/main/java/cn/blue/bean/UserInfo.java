package cn.blue.bean;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserInfo {
	/**
	 * 用户id
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 最近更新人的id
	 */
	private Integer lastUserId;
	/**
	 * 头像
	 */
	private String headPortrait;
	/**
	 * 状态
	 * 1 激活状态
	 * 0 禁用状态
	 */
	private Integer status;
	/**
	 * 角色
	 */
	private List<Role> roles;
}
