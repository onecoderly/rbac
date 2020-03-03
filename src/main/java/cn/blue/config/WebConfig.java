package cn.blue.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.blue.interceptor.LoginInterceptor;
import cn.blue.interceptor.PowerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Resource
	private PowerInterceptor powerInterceptor;
	@Resource
	private LoginInterceptor loginInterceptor;

	@Override
	//登录拦截器加在权限拦截器前
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/login","/logout","/error")
		.excludePathPatterns("/layui/**","/**/*.js");
		registry.addInterceptor(powerInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/login","/logout")
		.excludePathPatterns("/layui/**","/**/*.js")
		.excludePathPatterns("/**/*.jpg","/**/*.png");//给图片放行
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

}
