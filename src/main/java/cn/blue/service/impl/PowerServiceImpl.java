package cn.blue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.blue.annotation.Role;
import cn.blue.bean.Power;
import cn.blue.mapper.PowerMapper;
import cn.blue.service.PowerService;
@Service("/powerService")
public class PowerServiceImpl implements PowerService {
	@Resource
	private PowerMapper powerMapper;
	
	@Role("超级管理员")
	@Override
	public List<Power> find(Power power) {
		return powerMapper.find(power);
	}

	@Override
	public boolean insert(Power power) {
		return powerMapper.insert(power) > 0;
	}

	@Override
	public boolean update(Power power) {
		return powerMapper.update(power) > 0;
	}

	@Override
	public Power findById(Integer id) {
		return powerMapper.findById(id);
	}

	@Override
	public boolean delete(Integer[] ids) {
		return powerMapper.delete(ids) > 0;
	}

	@Override
	public boolean addPowerByPId(Power power) {
		return powerMapper.addPowerByPId(power) > 0;
	}

}
