package cn.blue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.blue.bean.Role;

/**
 * 
 * @author Blue
 *
 */
public interface RoleMapper {
	/**
	 * 查角色所有
	 * @param role
	 * @return 角色集合
	 */
	List<Role> find(Role role);
	
	/**
	 * 修改角色名
	 * @param role
	 * @return
	 */
	int updateName(@Param("role")Role role);
	
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	int delete(Integer id);
	
	/**
	 * 新增角色名
	 * @param role
	 * @return
	 */
	int insert(Role role);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteByIds(@Param("ids") Integer... ids);
	
	/**
	 * 根据角色名模糊查询角色
	 * @param role
	 * @return
	 */
	List<Role> selectOneRole(Role role);
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	Role findOneRole(Role role);
	
	/**
	 * 根据用户id来查角色
	 * @param userId
	 * @return
	 */
	List<Role> findRoleByUserId(@Param("userId")Integer userId);
	
	/**
	 * 根据用户id删除角色
	 * @param id
	 * @return
	 */
	int deleteRoleByUserId(@Param("userId")Integer id);
	
	/**
	 * 根据角色id新增权限
	 * @param roleId
	 * @param powerIds
	 * @return
	 */
	int addPowerMapping(@Param("roleId")Integer roleId, Integer... powerIds);
}
