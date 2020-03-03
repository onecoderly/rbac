package cn.blue.aop;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cn.blue.annotation.Role;
import cn.blue.bean.UserInfo;
import cn.blue.consts.ApplicationConst;
import cn.hutool.core.util.ReflectUtil;

@Component
@Aspect
public class PowerAdvice {
	@Resource
	private HttpSession session;

	// 定义切入点，拦截注解
	@Pointcut("@annotation(cn.blue.annotation.Role)")
	public void pointcut() {
	}

	// 环绕通知
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object object = point.getTarget();
		// 得到方法
		Signature signature = point.getSignature();
		// 得到登录对象
		UserInfo user = (UserInfo) session.getAttribute(ApplicationConst.USER_STATUS_SESSION);
		// 取得方法对象
		Method method = ReflectUtil.getMethodByName(signature.getDeclaringType(), signature.getName());
		List<cn.blue.bean.Role> roles = user.getRoles();
		Role role = null;
		if (method.isAnnotationPresent(Role.class)) {
			role = method.getAnnotation(Role.class);
		} else if (signature.getDeclaringType().isAnnotationPresent(Role.class)) {
			role = (Role) signature.getDeclaringType().getAnnotation(Role.class);
		}
		if (role != null) {
			String[] roles2 = role.value();
			if (roles == null || roles.isEmpty() || roles2 == null || roles2.length == 0) {
				throw new RuntimeException("你无权访问该方法");
			}
			for (cn.blue.bean.Role r : roles) {
				for (String r2 : roles2) {
					if (!r.getName().equals(r2)) {
						throw new RuntimeException("你无权访问该方法");
					}
				}
			}
		}
		return point.proceed();
	}
}
