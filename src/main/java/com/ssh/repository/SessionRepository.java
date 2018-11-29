package com.ssh.repository;

import org.hibernate.Session;

public interface SessionRepository {
	public Session getCurrentSession();
}
