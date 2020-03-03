package cn.blue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.blue.bean.Power;

public interface PowerMapper {
	/**
	 * 查所有
	 * @param power
	 * @return Power集合
	 */
	public List<Power> find(Power power);
	/**
	 * 新增
	 * @param power
	 * @return 受影响行数
	 */
	public int insert(Power power);
	
	/**
	 *  根据pid来新增子权限
	 * @param power
	 * @return 受影响行数
	 */
	public int addPowerByPId(Power power);
	/**
	 * 逻辑删除
	 * @param power
	 * @return 受影响行数
	 */
	public int update(Power power);
	/**
	 * 根据用户id查找
	 * @param id
	 * @return Power
	 */
	public Power findById(@Param("id") Integer id);
	/**
	 * 批量删除
	 * @param ids
	 * @return s
	 */
	public int delete(@Param("ids")Integer ...ids);

	/**
	 * 根据角色id删除权限
	 * @param roleId
	 * @return
	 */
	int deletePowerMappingByRoleId(Integer roleId);
	
	/**
	 * 根据角色id来查找权限
	 * @param roleId
	 * @return
	 */
	List<Power> findByRoleId(@Param("roleId")Integer roleId);
}
