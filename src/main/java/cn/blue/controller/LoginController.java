package cn.blue.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.blue.bean.Power;
import cn.blue.bean.Role;
import cn.blue.bean.UserInfo;
import cn.blue.consts.ApplicationConst;
import cn.blue.service.UserInfoService;

@Controller
public class LoginController {
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private ApplicationContext applicationContext;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

	@PostMapping("/login")
	public String login(UserInfo user, HttpSession session) {
		UserInfo userInfo = userInfoService.login(user);
		if (userInfo != null) {
			session.setAttribute(ApplicationConst.USER_STATUS_SESSION, userInfo);
			// 权限去重
			List<Role> roles = userInfo.getRoles();
			Set<Integer> powerIds = new HashSet<>();
			List<Power> powers = new ArrayList<>();
			List<Power> menus = new ArrayList<>();
			Map<Integer,Power> menuMap=new HashMap<>();
			if (roles != null) {
				// 资源权限处理
				roles.forEach(r -> {
					List<Power> list = r.getPowers();
					if (list != null) {
						list.forEach(p -> {
							if (powerIds.add(p.getId())) {
								powers.add(p);
							}
						});
					}
				});
				// 菜单权限处理
				
				powers.stream().filter(p -> p.getType() == 1).forEach(p -> {
					menuMap.put(p.getId(), p);
					p.setChilds(new ArrayList<>());//清空子权限
				});
				for (Entry<Integer, Power> entry : menuMap.entrySet()) {
					Power p = entry.getValue();
					if(p.getPid() != null) {
						Power power = menuMap.get(p.getPid());
						if(power!=null) {
							//重建子权限与父权限关系
							power.getChilds().add(p);
						}
					}else {
						menus.add(p);
					}
				}
			}
			session.setAttribute(ApplicationConst.USER_MENUS_SESSION, menus);
			session.setAttribute(ApplicationConst.USER_POWERS_SESSION, powers);
			return "redirect:/";
		}
		return "login";
	}
}
