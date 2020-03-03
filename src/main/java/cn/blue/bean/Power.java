package cn.blue.bean;

import java.util.List;

import lombok.Data;

@Data
public class Power {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 父权限id
	 */
	private Integer pid;
	/**
	 * 权限名称
	 */
	private String name;
	/**
	 * 权限URL
	 */
	private String url;
	/**
	 * 状态 
	 * 	默认1
	 */
	private Integer status;
	/**
	 * 是否被选中
	 */
	private boolean checked;
	/**
	 * 权限类型：
	 * 0 资源
	 * 1 菜单
	 */
	private Integer type;
	/**
	 * 父权限
	 */
	private Power parent;
	/**
	 * 子权限列表
	 */
	private List<Power> childs;
	
	/**
	 * 角色列表
	 */
	private List<Role> roles;
}
