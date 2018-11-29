package com.ssh.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.entity.User;
import com.ssh.repository.UserRepository;
import com.ssh.service.MailService;
import com.ssh.service.UserService;
import com.ssh.utils.SystemUtils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailService mailService;
	
	public void addUser(User user) {
		// TODO Auto-generated method stub
		userRepository.saveUser(user);
	}

	public User queryUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.queryUser(user);
	}

	public User getUserById(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.findUserById(id);
	}

	public User findUserPassword(String userName, String email) {
		// TODO Auto-generated method stub
		return userRepository.findUserPassword(userName, email);
	}

	public List<User> findUsers(Map<String, Object> map) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("from User where 1=1 ");
		
		List<Object> params = new LinkedList<Object>();
		
		if (map.get("userName")!=null) {
			hql.append(" and userName=? ");
			params.add(map.get("userName"));
		}
		if (map.get("email")!=null) {
			hql.append(" and email=? ");
			params.add(map.get("email"));
		}
		if (map.get("sex")!=null) {
			hql.append(" and sex=? ");
			params.add(map.get("sex"));
		}
		if (map.get("validateOverDate")!=null) {
			hql.append(" and validateOverDate=? ");
			params.add(map.get("validateOverDate"));
		}
		if (map.get("validateSerCode")!=null) {
			hql.append(" and validateSerCode=? ");
			params.add(map.get("validateSerCode"));
		}
		if (map.get("userNameEncodes")!=null) {
			hql.append(" and userNameEncodes=? ");
			params.add(map.get("userNameEncodes"));
		}
		
		System.out.println(hql.toString());

		return userRepository.findUser(params, hql.toString());
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userRepository.updateUser(user);
	}

	public void saveUser(User user) {
		// TODO Auto-generated method stub
		userRepository.saveUser(user);
		
		if (findUserByUserName(user.getUserName()) == null) {
			throw new RuntimeException("insert "+user.getUserName()+" error");
		}
	}

	public User findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findUserByUserName(userName);
	}

}
