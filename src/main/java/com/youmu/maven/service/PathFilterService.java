package com.youmu.maven.service;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathFilterService {

	@Autowired
	private SecurityManager securityManager;
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;

	public void change() {
		shiroFilterFactoryBean.getFilters();

	}
}
