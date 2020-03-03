package cn.blue.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.blue.bean.UserInfo;
import cn.blue.consts.ApplicationConst;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute(ApplicationConst.USER_STATUS_SESSION);
		if (user != null) {
			// 放行
			return true;
		}
		// 拦截，跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/login");
		return false;
	}

}
