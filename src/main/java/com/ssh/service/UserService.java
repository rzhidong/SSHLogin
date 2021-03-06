package com.ssh.service;

import java.util.List;
import java.util.Map;

import com.ssh.entity.User;

public interface UserService {
	
	public void addUser(User user);
	
	public User queryUser(User user);
	
	public User getUserById(Integer id);
	
	public User findUserPassword(String userName,String email);
	
	public List<User> findUsers(Map<String, Object> map);
	
	public void updateUser(User user);
	
	public void saveUser(User user);
	
	public User findUserByUserName(String userName);
	
}