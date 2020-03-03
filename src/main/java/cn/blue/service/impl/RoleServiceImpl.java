package cn.blue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.blue.bean.Role;
import cn.blue.mapper.PowerMapper;
import cn.blue.mapper.RoleMapper;
import cn.blue.service.RoleService;
@Service("/roleService")
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private PowerMapper powerMapper;

	@Override
	public List<Role> find(Role role) {
		return roleMapper.find(role);
	}

	@Override
	public boolean updateName(Role role) {
		return roleMapper.updateName(role) > 0;
	}

	@Override
	public boolean delete(Integer id) {
		return roleMapper.delete(id) > 0;
	}

	@Override
	public boolean insert(Role role) {
		return roleMapper.insert(role) > 0;
	}

	@Override
	public Role findOneRole(Role role) {
		return roleMapper.findOneRole(role);
	}

	@Override
	public boolean deleteById(Integer... ids) {
		return roleMapper.deleteByIds(ids) > 0;
	}

	@Override
	public List<Role> selectOneRole(Role role) {
		return roleMapper.selectOneRole(role);
	}
	
	@Transactional
	@Override
	public boolean addPowerMapping(Integer roleId, Integer... powerIds) {
		this.powerMapper.deletePowerMappingByRoleId(roleId);
		roleMapper.addPowerMapping(roleId, powerIds);
		return true;
	}
}
