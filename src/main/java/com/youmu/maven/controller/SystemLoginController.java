package com.youmu.maven.controller;

import com.alibaba.fastjson.JSONObject;
import com.youmu.maven.JsonView;
import com.youmu.maven.service.PathFilterService;
import com.youmu.maven.shiro.realm.MyRealm;
import com.youmu.maven.shiro.surppot.UrlFilterInfo;
import com.youmu.maven.shiro.system.ShiroFilterChainManager;
import com.youmu.maven.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/system")
public class SystemLoginController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ShiroFilterChainManager shiroFilterChainManager;

	@Autowired
	private AuthorizingRealm realm;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password) {
		request.getParameterMap().get("username");
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "Error";
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			result = "Error";
			map.put("message", "用户名/密码不能为空");
		} else {
			try {

				Subject subject = SecurityUtils.getSubject();
				// 先检测当前用户是否登录了
				if (!subject.isAuthenticated()) {

					UsernamePasswordToken token = new UsernamePasswordToken();
					token.setUsername(username);
					token.setPassword(password.toCharArray());
					subject.login(token);
					// 检测是否登录 成功
					if (subject.isAuthenticated()) {
						result = "Success";
						map.put("message", "成功");
					} else {
						ModelAndView modelAndView = new ModelAndView(
								"redirect:/jsp/login.jsp");
						return modelAndView;
					}
				} else {
					ModelAndView modelAndView = new ModelAndView(
							"redirect:/jsp/login.jsp");
					return modelAndView;
				}
			} catch (AuthenticationException e) {
				// TODO: handle exception
				result = "Error";
				map.put("message", "用户名或者密码错误");
			}
		}
		map.put("result", result);
		map.put("user",
				SecurityUtils.getSubject().getSession()
						.getAttribute("currentUser"));
		return new ModelAndView(new JsonView(JSONObject.toJSONString(map)));
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String Login() {
		return "/login";

	}

	@RequestMapping("/admin")
	@ResponseBody
	public String adminPerm() {
		return "aaaa";
	}

	@RequestMapping("/clear")
	public ModelAndView clearCache() {
		((MyRealm) realm).clearCache();
		return new ModelAndView(new JsonView("{\"code\":1}"));
	}

	@RequestMapping("/unauthorized")
	public String unauthorized() {
		return "redirect:/jsp/unauthorized.jsp";
	}

	@RequestMapping("/logout")
	@ResponseBody
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			subject.logout();
		}
		return "success";
	}

	@Autowired
	PathFilterService service;

	@RequestMapping("/test")
	@ResponseBody
	public String test() throws Exception {

		List<UrlFilterInfo> infos = new ArrayList<UrlFilterInfo>();
		UrlFilterInfo info1 = new UrlFilterInfo();
		info1.setUrl("/system/admin");
		info1.setFilterName(UrlFilterInfo.FilterType.PERMISSIONS);
		List<String> contents = new ArrayList<String>();
		Collections.addAll(contents, new String[] { "user_create" });
		info1.setContents(contents);
		infos.add(info1);
		shiroFilterChainManager.applyChains(infos);
		return "";
	}

	@RequestMapping("/test22")
	@ResponseBody
	public String test22() throws Exception {

		System.out.println(id);
		return "";
	}

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
