package com.youmu.maven.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.youmu.maven.entity.Permission;

@Repository
public interface PermissionDao {
	public List<Permission> getAllPermission();

	public Permission getPermission(Permission permission);

	public int deletePermission(Permission permission);

	public int insertPermission(Permission permission);

	public int updatePermission(Permission permission);

	public List<Permission> getPermissionsByRoleId(@Param("id") Integer id);
}
