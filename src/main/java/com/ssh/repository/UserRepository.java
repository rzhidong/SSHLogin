package com.ssh.repository;

import java.util.List;

import com.ssh.entity.User;

public interface UserRepository {
	
public void saveUser(User user);
	
	public User queryUser(User user);
	
	public User findUserById(Integer id);
	
	public User findUserPassword(String userName,String email);
	
	public List<User> findUser(List<Object> params,String hql);
	
	public void updateUser(User user);
	
	public User findUserByUserName(String userName);

}
