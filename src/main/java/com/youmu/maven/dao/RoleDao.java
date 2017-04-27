package com.youmu.maven.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.youmu.maven.entity.Role;

@Repository
public interface RoleDao {
	public List<Role> getAllRole();

	public Role getRole(Role role);

	public int deleteRole(Role role);

	public int insertRole(Role role);

	public int updateRole(Role role);

	public List<Role> getRoleByUserId(@Param("id") Integer id);
}
