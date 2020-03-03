package cn.blue.controller;

import java.util.List;

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
/**
 * 角色管理
 * @author Blue
 *
 */
@Controller
@Slf4j
@RequestMapping("/role")
public class RoleController {
	@Resource
	private RoleService roleService;
	@Resource
	private PowerService powerService;
	
	@GetMapping
	public String home() {
		return "role/list";
	}
	
	@GetMapping("/ed")
	public String hom() {
		return "role/editor";
	}
	
	@PostMapping
	@ResponseBody
	public Result fing(
			@RequestParam(name = "page",required = false, defaultValue = "1")Integer page,
			@RequestParam(name = "limit",required = false, defaultValue = "10")Integer limit,
			Role role) {
			PageHelper.startPage(page, limit);
			List<Role> list = roleService.find(role);
			PageInfo<Role> pageInfo = new PageInfo<>(list);
			Result r = Result.success();
			r.addItem("count", pageInfo.getTotal());
			r.addItem("data", list);
			r.setMsg("查询成功");
			return r;
	}
	
	@GetMapping("/powerMapping/{roleId}")
	public String powerMapping(@PathVariable("roleId")Integer roleId) {
		// 请求转发，将获得的roleId传到下个页面去
		return "role/powerMapping";
	}
	
	@PostMapping("/powerMapping/{roleId}")
	@ResponseBody
	public AResult powerMapping(@PathVariable("roleId")Integer roleId, Integer[] powerIds) {
		try {
			if(roleService.addPowerMapping(roleId, powerIds)) {
				return AResult.success().setCode(0).setMsg("权限关联成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AResult.error(500).setMsg("权限关联失败");
	}
	
	@GetMapping(path = {"/editor/{roleId}","/editor"})
	public String editor(@PathVariable(required = false,name = "roleId")Integer roleId, ModelMap map) {
		Role role = new Role();
		role.setId(roleId);
		Role list = roleService.findOneRole(role);
		map.put("role", list);
		return "role/editor";
	}
	
	
	@GetMapping("/add")
	public String add() {
		return "role/add";
	}
	/**
	 * 逻辑删除角色
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	@ResponseBody
	public AResult delete(@RequestParam("id")Integer id) {
		try {
			if(roleService.delete(id)) {
				return AResult.success().setCode(0).setMsg("删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AResult.error(500).setMsg("删除失败");
	}
	
	/**
	 * 新增角色名
	 * @param role
	 * @return
	 */
	@GetMapping("/insert")
	@ResponseBody
	public AResult insert(Role role) {
		try {
			if(role.getId() != null) {
				if(roleService.updateName(role)) {
					return AResult.success().setCode(0).setMsg("修改成功");
				}
			} else {
				//没写新增sql
				if(roleService.insert(role)) {
					return AResult.success().setCode(0).setMsg("新增角色名成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AResult.error(500).setMsg("新增角色名失败");
	}
	
	/**
	 * 根据角色名模糊查询角色
	 * @param role
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public AResult findOneRole(Role role) {
		try {
			log.info("角色：{}"+role);
			if(role.getName() == null) {
				role.setName(null);
			}
			List<Role> list = roleService.selectOneRole(role);
			return AResult.success().setCode(0).setMsg("查询成功").add("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			return AResult.error(500).setMsg("查询失败").add("error", e.getMessage());
		}
	}
	
	@GetMapping("/del")
	@ResponseBody
	public AResult deleteByIds(Integer[] ids) {
		try {
			if(roleService.deleteById(ids)) {
				return AResult.success().setCode(0).setMsg("批量删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AResult.error(500).setMsg("批量删除失败");
	}
}
