package com.youmu.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youmu.maven.entity.User;

@Repository
public interface UserDao {
	public List<User> getAllUser();

	public User getUser(User user);

	public int deleteUser(User user);

	public int insertUser(User user);

	public int updateUser(User newUser);

}
