package com.roms.module.user.service;

import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.roms.module.user.domain.dao.UserDao;
import com.roms.module.user.domain.dto.UserCreateDto;
import com.roms.module.user.domain.model.User;

@Service("userService")
public class UserService  {
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void save(User user)
	{
		this.userDao.save(user);
	}
	
	public User find(long id)
	{
		return this.userDao.find(id);
	}
	
	public List<User> findAll()
	{
		return this.userDao.findAll();
	}

	public void delete(long id) 
	{
		User user = this.find(id);
		
		if (user == null) {
			throw new ObjectNotFoundException(id, "user");
		}
		
		this.userDao.delete(user);
	}

	public User create(UserCreateDto userDto) {
		
    	User user = new User();
    	user.setFirstname(userDto.getFirstname());
    	user.setLastname(userDto.getLastname());
    	user.setUsername(userDto.getUsername());
    	user.setUsernameCanonical(userDto.getUsername());
    	user.setEmail(userDto.getEmail());	
    	user.setSalt(userDto.getPassword());
    	user.setPassword(userDto.getPassword());
    	user.setActive(0);
    	user.setCreatedAt(LocalDateTime.now());
    	user.setRole("user");
    	this.save(user);

		return user;
	}

}
