package cn.blue.service;

import java.util.List;

import cn.blue.bean.Power;

public interface PowerService {
	/**
	 * 查所有
	 * @param power
	 * @return Power集合
	 */
	public List<Power> find(Power power);
	/**
	 * 新增
	 * @param power
	 * @return
	 */
	public boolean insert(Power power);
	/**
	 * 根据pid来新增子权限
	 * @param power
	 * @return 
	 */
	public boolean addPowerByPId(Power power);
	
	/**
	 * 逻辑删除
	 * @param power
	 * @return 
	 */
	public boolean update(Power power);
	/**
	 * 根据id查用户信息
	 * @param id
	 * @return Power
	 */
	public Power findById(Integer id);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean delete(Integer[] ids);
}
