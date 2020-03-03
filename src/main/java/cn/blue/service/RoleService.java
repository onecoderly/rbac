package cn.blue.service;

import java.util.List;

import cn.blue.bean.Role;

/**
 * 
 * @author Blue
 *
 */
public interface RoleService {
	/**
	 * 
	 * @param role
	 * @return 角色信息
	 */
	List<Role> find(Role role);
	
	/**
	 * 修改角色名
	 * @param name
	 * @return
	 */
	boolean updateName(Role role);
	
	/**
	 * 逻辑删除角色
	 * @param id
	 */
	boolean delete(Integer id);
	
	/**
	 * 新增角色
	 * @param role
	 */
	boolean insert(Role role);
	
	/**
	 * 根据角色名查询角色
	 * @param role
	 * @return
	 */
	Role findOneRole(Role role);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return 
	 */
	boolean deleteById(Integer... ids);
	
	/**
	 * 根据角色名模糊查询角色
	 * @param role
	 * @return
	 */
	List<Role> selectOneRole(Role role);
	
	/**
	 * 新增权限
	 * @param roleId
	 * @param powerIds
	 * @return
	 */
	boolean addPowerMapping(Integer roleId, Integer... powerIds);
}
