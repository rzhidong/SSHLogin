package com.ssh.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.repository.SessionRepository;

@Repository("sessionRepository")
public class SessionRepositoryImpl implements SessionRepository{
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
}
