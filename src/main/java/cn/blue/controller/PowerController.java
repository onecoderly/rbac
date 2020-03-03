package cn.blue.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.blue.bean.Power;
import cn.blue.bean.Role;
import cn.blue.bean.vo.Result;
import cn.blue.model.AResult;
import cn.blue.service.PowerService;
import cn.blue.service.RoleService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/power")
public class PowerController {
	@Resource
	private PowerService powerService;
	@Resource
	private RoleService roleService;
	@GetMapping
	public String home() {
		return "power/list";
	}
	
	@PostMapping("/{roleId}")
	@ResponseBody
	public AResult find(Power power,@PathVariable("roleId")Integer roleId){
		List<Power> powers = powerService.find(power);
		Role r = new Role();
		r.setId(roleId);
		Role role = roleService.findOneRole(r);
		List<Power> list = role.getPowers();
		Map<Integer, Power> powerMap = new HashMap<>();
		if (powers != null) {
			for (Power p : powers) {
				powerMap.put(p.getId(), p);
			}
		}
		if (list != null) {
			for (Power p : list) {
				if (powerMap.containsKey(p.getId())) {
					if(p.getPid() != null) {
						Power parent = powerMap.get(p.getPid());
						List<Power> childs = parent.getChilds();
						if(childs  == null) {
							childs=new ArrayList<>();
							parent.setChilds(childs);
						}
						p.setChecked(true);
						for (int i = 0,length=childs.size(); i < length; i++) {
							if(p.getId() == childs.get(i).getId()) {
								childs.set(i, p);
							}
						}
					}else {
						powerMap.get(p.getId()).setChecked(true);
					}
					
				}
			}
		}
		return AResult.success().setCode(0).setMsg("查询成功").add("powers", powers);
	}
	
	@GetMapping(path = {"/editor/{id}","/editor"})
	public String editor(@PathVariable(required = false,name = "id")Integer id,ModelMap map) {
		if(id != null) {
			// 根据id查询用户信息
			Power power = powerService.findById(id);
			map.put("power", power);
		}
		return "power/editor";
	}
	
	@GetMapping(path = {"/update/{id}","/update"})
	public String update(@PathVariable(required = false,name = "id")Integer id,ModelMap map) {
		if(id != null) {
			// 根据id查询用户信息
			Power power = powerService.findById(id);
			map.put("power", power);
		}
		return "power/update";
	}
	
	@GetMapping("/add")
	public String add() {
		return "power/add";
	}
	
	@PostMapping("/del")
	@ResponseBody
	// 接收批量删除-前端传过来的id值-方式：第一种@RequestParam("ids[]")
	public Result delete(Integer[] ids) {
		try {
			if(powerService.delete(ids)) {
				// 逻辑删除
				return Result.success().setMsg("删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.error(500, "删除失败");
	}
	
	@PostMapping("/editor")
	@ResponseBody
	//@RequestBody将json对象转换为实例对象
	public Result editor(Power power) {
		try {
			if(power.getId() != null) {
				if(powerService.addPowerByPId(power)) {
					return Result.success().setMsg("新增子权限成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return Result.error(500,"新增子权限失败");
	}
	
	@PostMapping("/update")
	@ResponseBody
	//@RequestBody将json对象转换为实例对象
	public Result update(Power power) {
		try {
			if(powerService.update(power)) {
				log.debug("修改的[{}]:",power);
				return Result.success().setMsg("修改权限名成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return Result.error(500,"修改权限名失败");
	}
	
	@PostMapping("/add")
	@ResponseBody
	//@RequestBody将json对象转换为实例对象
	public Result add(Power power) {
		try {
			if(powerService.insert(power)) {
				return Result.success().setMsg("新增父权限成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return Result.error(500,"新增权限失败");
	}
	
	
	@PostMapping
	@ResponseBody
	public Result fing(
			@RequestParam(name = "page",required = false, defaultValue = "1")Integer page,
			@RequestParam(name = "limit",required = false, defaultValue = "10")Integer limit, 
			Power power) {
		PageHelper.startPage(page, limit);
		if(power.getStatus() != null && power.getStatus() < 0) {
			// 如果value值=-1，则查询所有
			power.setStatus(null);
		}
		if(power.getType() != null && power.getType() < 0) {
			// 如果type值=-1，则查询所有
			power.setType(null);
		}
		List<Power> list = powerService.find(power);
		PageInfo<Power> pageInfo = new PageInfo<>(list);
		Result r = Result.success();
		r.addItem("count", pageInfo.getTotal());
		r.addItem("data", list);
		r.setMsg("查询成功");
		return r;
	}
}
