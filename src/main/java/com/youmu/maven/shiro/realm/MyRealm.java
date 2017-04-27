package com.youmu.maven.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.youmu.maven.dao.PermissionDao;
import com.youmu.maven.dao.RoleDao;
import com.youmu.maven.dao.UserDao;
import com.youmu.maven.entity.Permission;
import com.youmu.maven.entity.Role;
import com.youmu.maven.entity.User;

public class MyRealm extends AuthorizingRealm {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		AuthenticationInfo info = null;
		User user = new User();
		user.setName(userToken.getUsername().trim());
		user.setPassword(String.valueOf(userToken.getPassword()).trim());
		user = userDao.getUser(user);
		if (user != null) {
			info = new SimpleAuthenticationInfo(user.getName(),
					user.getPassword(), getName());
			try {
				SecurityUtils.getSubject().getSession()
						.setAttribute("currentUser", user);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = null;
		try {
			user = (User) SecurityUtils.getSubject().getSession()
					.getAttribute("currentUser");
			user.setRoles(roleDao.getRoleByUserId(user.getId()));
			for (Role role : user.getRoles()) {
				info.addRole(role.getName());
				role.setPermissions(permissionDao.getPermissionsByRoleId(role
						.getId()));
				for (Permission permission : role.getPermissions()) {
					org.apache.shiro.authz.Permission p = new WildcardPermission(
							permission.getName());
					info.addObjectPermission(p);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return info;
	}

	public void clearCache() {
		// TODO Auto-generated constructor stub
		this.clearCache(SecurityUtils.getSubject().getPrincipals());
	}

}
