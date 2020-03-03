package cn.blue.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.blue.bean.Power;
import cn.blue.bean.Role;
import cn.blue.bean.UserInfo;
import cn.blue.consts.ApplicationConst;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
//加入spring容器
public class PowerInterceptor extends HandlerInterceptorAdapter {
	// 已经存在session中了
//	private Map<Integer,List<Power>> powerMap = new HashMap<>();
//	
//	@EventListener //登录触发事件
//	private void loginEvent(UserInfo user) {
//		if (user != null) {
//			// 用来存放已经去重的权限
//			List<Role> roles = user.getRoles();
//			Set<Integer> powerIds = new HashSet<>();
//			List<Power> powers = new ArrayList<>();
//			if(roles!=null) {
//				roles.forEach(r->{
//					List<Power> list = r.getPowers();
//					if(list != null) {
//						list.forEach(p -> {
//							if(powerIds.add(p.getId())) {
//								if(StrUtil.isNotBlank(p.getUrl())) {
//									powers.add(p);
//								}
//							}
//						});
//					}
//				});
//			}
//			powerMap.put(user.getId(), powers);
//			log.debug("用户[{}]的权限数量已更新为：{}",user.getId(),powers.size());
//		}
//	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 鉴权操作
		// 当前请求的uri
		String uri = request.getRequestURI();
		// 去除ContextPath
		String contextPath = request.getContextPath();
		// contextPath/path
		uri = uri.substring(uri.indexOf(contextPath) + contextPath.length());
		// 1.得到当前登录的用户
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute(ApplicationConst.USER_STATUS_SESSION);
		if (user != null) {
			List<Power> list = (List<Power>) session.getAttribute(ApplicationConst.USER_POWERS_SESSION);
			if (list != null) {
				AntPathMatcher pm = new AntPathMatcher();
				for (Power power : list) {
					// 鉴权操作
					if (StrUtil.isNotBlank(power.getUrl()) && pm.match(power.getUrl(), uri)) {
						return true;
					}
				}
			}
		}
		response.sendError(403, "您无权访问该资源,如需访问请联系管理员");
		// 2.得到用户拥有的角色
		// 3.得到权限列表
		return false;
	}
}
