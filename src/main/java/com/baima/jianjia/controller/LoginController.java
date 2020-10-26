package com.baima.jianjia.controller;
/*
 * 用于登录和注销的控制器
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
	//进入登录页的方法
	@RequestMapping("/login")
	public ModelAndView Hellologin() {
		return new ModelAndView("login");
	}
	//登录的方法
	@PostMapping(value = "/tologin")
	public String login(String username, String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			System.out.println("{\"info\":\"登录成功\",\"code\":0}");
			return "{\"info\":\"登录成功\",\"code\":0}";
		} catch (UnknownAccountException e) {
			return "{\"info\":\"用户名不存在\",\"code\":1}";
		} catch (IncorrectCredentialsException e) {
			return "{\"info\":\"密码错误\",\"code\":2}";
		}
	}
	//注销的方法
	@RequestMapping("/logout")
	public ModelAndView logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return new ModelAndView("index");
	}
}
