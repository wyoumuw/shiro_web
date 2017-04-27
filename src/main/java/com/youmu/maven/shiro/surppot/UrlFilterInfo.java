package com.youmu.maven.shiro.surppot;

import java.util.List;

public class UrlFilterInfo {
	public static enum FilterType {
		PERMISSIONS("perms"), ROLES("roles");
		private String name;

		FilterType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private String url;
	private List<String> contents;
	private FilterType filterName;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public FilterType getFilterName() {
		return filterName;
	}

	public void setFilterName(FilterType filterName) {
		this.filterName = filterName;
	}

	public String getChainFilterConfig() {
		StringBuffer sb = new StringBuffer();
		for (String content : contents) {
			sb.append(content);
			sb.append(",");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}

}
