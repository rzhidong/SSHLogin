package com.ssh.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ssh.entity.User;
import com.ssh.repository.UserRepository;

@Repository("userRepository")
public class UserRepositoryImpl extends SessionRepositoryImpl implements UserRepository{

	public void saveUser(User user) {
		// TODO Auto-generated method stub
		getCurrentSession().save(user);
	}
	
	@SuppressWarnings("unchecked")
	public User queryUser(User user) {
		// TODO Auto-generated method stub
		String hql = "from User where userName=? and password=?";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0, user.getUserName());
		query.setParameter(1, user.getPassword());
		
		User currentUser = null;
		
		
		List<User> userList = query.list();
		if (userList != null && userList.size() > 0) {
			currentUser = userList.get(0);
		}
		
		return currentUser;
	}

	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		String hql = "from User where id=?";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		User user = (User) query.uniqueResult();
		return user;
	}

	public User findUserPassword(String userName, String email) {
		// TODO Auto-generated method stub
		String hql="from User where userName=? and email=?";
		Query query=getCurrentSession().createQuery(hql);
		query.setParameter(0, userName);
		query.setParameter(1, email);
		User currentUser=(User) query.uniqueResult();
		
		return currentUser;
	}

	@SuppressWarnings("unchecked")
	public List<User> findUser(List<Object> params, String hql) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(hql);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		return query.list();
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		getCurrentSession().update(user);
	}

	public User findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		String hql="from User where userName=?";
		Query query=getCurrentSession().createQuery(hql);
		query.setParameter(0, userName);
		return (User) query.uniqueResult();
	}

}
