package com.roms.module.user.domain.dao;

import org.hibernate.criterion.Restrictions;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import com.roms.module.user.domain.model.User;

@Repository("userDao")
@Transactional 
public class UserDao {
	
	@Autowired
	@Qualifier("sessionFactory")
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	public void save(User user)
	{
		this.getSession().save(user);
	}
	
	public User findByEmail(String email)
	{
		return (User) this.getSession().createCriteria( User.class ).
								   add(Restrictions.eq("email", email)).
								   uniqueResult();
	}
	
	public User find(long id)
	{
		return (User) this.getSession().get(User.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAll()
	{
		return (List<User>) this.getSession().createQuery("from User").list();
	}

	public void delete(User user) 
	{
		this.getSession().delete(user);
	}

}
