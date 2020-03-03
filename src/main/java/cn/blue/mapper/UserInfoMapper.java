package cn.blue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.blue.bean.UserInfo;

public interface UserInfoMapper {
	/**
	 * 查所有
	 * @param user
	 * @return UserInfo集合
	 */
	public List<UserInfo> find(UserInfo user);
	/**
	 * 新增
	 * @param user
	 * @return 受影响行数
	 */
	public int insert(UserInfo user);
	/**
	 * 逻辑删除
	 * @param user
	 * @return 受影响行数
	 */
	public int update(UserInfo user);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public UserInfo findById(@Param("id")Integer id);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int delete(@Param("ids")Integer ...ids);
	
	/**
	 * 新增user表和role之间的关系
	 * @param id
	 * @param roleIds
	 * @return
	 */
	public int addRoleMapping(@Param("userId")Integer id, @Param("roles")Integer... roleIds);
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
	public int upload(UserInfo user);
}
