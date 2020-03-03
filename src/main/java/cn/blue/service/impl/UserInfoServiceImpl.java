package cn.blue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.blue.bean.UserInfo;
import cn.blue.mapper.RoleMapper;
import cn.blue.mapper.UserInfoMapper;
import cn.blue.service.UserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	@Resource
	private UserInfoMapper userInfoMapper;
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public List<UserInfo> find(UserInfo user) {
		return userInfoMapper.find(user);
	}

	@Override
	//事务
	@Transactional
	public boolean insert(UserInfo user, Integer[] roleIds) {
		user.setPassword(user.getUserName() + user.getPassword());
		if(userInfoMapper.insert(user) > 0) {
			if(roleIds == null || roleIds.length == 0) {
				return true;
			}
			if(userInfoMapper.addRoleMapping(user.getId(), roleIds) > 0) {
				return true;
			}
		}
		return false;
	}
	
	@Transactional
	@Override
	public boolean update(UserInfo user,Integer[] roleIds) {
		if(user.getPassword() != null) {
			user.setPassword(user.getUserName()+user.getPassword());
			if(userInfoMapper.update(user) > 0) {
				if(roleIds == null || roleIds.length == 0) {
					// 为空就不做数据的修改
					return true;
				}
				//修改：先删除再添加
				roleMapper.deleteRoleByUserId(user.getId());
				userInfoMapper.addRoleMapping(user.getId(), roleIds);
				return true;
			}
		} else {
			if(userInfoMapper.update(user) > 0) {
				if(roleIds == null || roleIds.length == 0) {
					return true;
				}
				//修改：先删除再添加
				roleMapper.deleteRoleByUserId(user.getId());
				userInfoMapper.addRoleMapping(user.getId(), roleIds);
				return true;
			}
		}
		return false;
	}

	@Override
	public UserInfo findById(Integer id) {
		return userInfoMapper.findById(id);
	}

	@Override
	public boolean delete(Integer[] ids) {
		return userInfoMapper.delete(ids) > 0;
	}

	@Override
	public UserInfo login(UserInfo user) {
		user.setPassword(user.getUserName()+user.getPassword());
		user.setStatus(1);
		return userInfoMapper.login(user);
	}

	@Override
	public boolean upload(UserInfo user) {
		return userInfoMapper.upload(user) > 0;
	}
	
	
}
