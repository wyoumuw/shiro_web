package com.youmu.maven.shiro.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youmu.maven.shiro.surppot.UrlFilterInfo;

@Service
public class ShiroFilterChainManager {

	@Autowired
	private ShiroFilterFactoryBean shiroFilter;
	private DefaultFilterChainManager filterChainManager;

	private Map<String, NamedFilterList> defaultFilterChains = null;

	public void applyChains(List<UrlFilterInfo> infos) {
		DefaultFilterChainManager filterChainManager = getFilterChainManager();
		// 保存默认的
		if (defaultFilterChains == null) {
			defaultFilterChains = new HashMap<String, NamedFilterList>(
					filterChainManager.getFilterChains());
		}
		// 清除以前的内容
		filterChainManager.getFilterChains().clear();
		for (UrlFilterInfo info : infos) {
			filterChainManager.addToChain(info.getUrl(), info.getFilterName()
					.getName(), info.getChainFilterConfig());
		}
		filterChainManager.getFilterChains().putAll(defaultFilterChains);
	}

	public DefaultFilterChainManager getFilterChainManager() {
		if (filterChainManager == null) {
			try {
				filterChainManager = (DefaultFilterChainManager) ((PathMatchingFilterChainResolver) ((AbstractShiroFilter) shiroFilter
						.getObject()).getFilterChainResolver())
						.getFilterChainManager();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return filterChainManager;
	}
}
