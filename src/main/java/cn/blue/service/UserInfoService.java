package cn.blue.service;

import java.util.List;

import cn.blue.bean.UserInfo;

public interface UserInfoService {
	/**
	 * 查所有
	 * @param user
	 * @return UserInfo集合
	 */
	public List<UserInfo> find(UserInfo user);
	/**
	 * 新增
	 * @param user
	 * @return 
	 */
	public boolean insert(UserInfo user, Integer[] roleIds);
	/**
	 * 逻辑删除
	 * @param user
	 * @param roleIds 
	 * @return 
	 */
	public boolean update(UserInfo user, Integer[] roleIds);
	/**
	 * 根据id查用户信息
	 * @param id
	 * @return UserInfo
	 */
	public UserInfo findById(Integer id);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean delete(Integer[] ids);
	
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public UserInfo login(UserInfo user);
	
	/**
	 * 上传图片
	 * @param user
	 * @return
	 */
	public boolean upload(UserInfo user);
	
}
