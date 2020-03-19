package cn.blue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.blue.bean.Role;
import cn.blue.bean.UserInfo;
import cn.blue.bean.vo.Result;
import cn.blue.consts.ApplicationConst;
import cn.blue.service.RoleService;
import cn.blue.service.UserInfoService;
import cn.blue.util.FileUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private RoleService roleService;
	@GetMapping
	public String home() {
		return "user/list";
	}
	@GetMapping(path = {"/editor/{id}","/editor"})
	public String editor(@PathVariable(required = false,name = "id")Integer id,ModelMap map) {
		List<Role> roles = roleService.find(null);
		map.put("roles", roles);
		if(id != null) {
			// 根据id查询用户信息
			UserInfo userInfo = userInfoService.findById(id);
			// 以时间换空间，空间复杂度
			Map<Integer,Role> roleMap = new HashMap<>();
			for(Role role : roles) {
				roleMap.put(role.getId(), role);
			}
			for(Role r : userInfo.getRoles()) {
				if(roleMap.containsKey(r.getId())) {
					roleMap.get(r.getId()).setChecked(true);
				}
			}
			
			// 时间复杂度
//			for(Role role : roles) {
//				for(Role r : userInfo.getRoles()) {
//					role.setChecked(role.getId() == r.getId());
//				}
//			}
			map.put("user", userInfo);
		}
		return "user/editor";
	}
	
	@PostMapping("/del")
	@ResponseBody
	// 接收批量删除-前端传过来的id值-方式：第一种@RequestParam("ids[]")
	public Result delete(Integer[] ids) {
		try {
			if(userInfoService.delete(ids)) {
				// 逻辑删除
				return Result.success().setMsg("禁用成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.error(500, "禁用失败");
	}
	
	@PostMapping("/editor")
	@ResponseBody
	//@RequestBody将json对象转换为实例对象;  加了事务外面异常可以被捕获，里面的不可以
	public Result editor(UserInfo user,Integer[] roleIds,ModelMap map) {
		List<Role> roles = roleService.find(null);
		map.put("roles", roles);
		try {
			if(user.getId() == null) {
				if(userInfoService.insert(user, roleIds)) {
					return Result.success().setMsg("新增用户成功");
				}
			}else {
				if(userInfoService.update(user, roleIds)) {
					return Result.success().setMsg("修改用户成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return Result.error(500,"新增用户失败");
	}
	
	@PostMapping("/upload")
	@ResponseBody
	//@RequestBody将json对象转换为实例对象;  加了事务外面异常可以被捕获，里面的不可以
	public Result upload(HttpServletRequest request,@RequestParam("file")MultipartFile file,UserInfo user) {
		String image = null;
		try {
			image = FileUtil.uploadFile(file);
        	UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ApplicationConst.USER_STATUS_SESSION);
        	userInfo.setHeadPortrait(image);
			if(userInfoService.upload(userInfo)) {
				return Result.success().setMsg("上传图片成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return Result.error(500,"上传图片失败");
	}
	
	@PostMapping
	@ResponseBody
	public Result fing(
			@RequestParam(name = "page",required = false, defaultValue = "1")Integer page,
			@RequestParam(name = "limit",required = false, defaultValue = "10")Integer limit, 
			UserInfo user) {
		PageHelper.startPage(page, limit);
		if(user.getStatus() != null && user.getStatus() < 0) {
			// 如果value值=-1，则查询所有
			user.setStatus(null);
		}
		List<UserInfo> list = userInfoService.find(user);
		// 移除超管账号
		list.remove(0);
		PageInfo<UserInfo> pageInfo = new PageInfo<>(list);
		Result r = Result.success();
		r.addItem("count", pageInfo.getTotal());
		r.addItem("data", list);
		r.setMsg("查询成功");
		return r;
	}
}
