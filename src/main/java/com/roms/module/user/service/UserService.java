package com.roms.module.user.service;

import java.util.Collection;
import com.roms.library.format.StringFormat;
import com.roms.module.user.domain.dao.RoleDao;
import com.roms.module.user.domain.model.Role;
import org.hibernate.ObjectNotFoundException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.roms.module.user.domain.dao.UserDao;
import com.roms.module.user.domain.dto.UserCreateDto;
import com.roms.module.user.domain.model.User;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserService  {

	@Autowired
	private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

	public void save(User user) {
		this.userDao.save(user);
	}
	
	public User find(long id) {
		return this.userDao.find(id);
	}

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User findByToken(String token) {
        return userDao.findByToken(token);
    }
	
	public Collection<User> findAll() {
		return this.userDao.findAll();
	}

    @Transactional
	public void delete(long id) {
		User user = this.find(id);
		if (user == null) {
			throw new ObjectNotFoundException(id, "user");
		}
		
		this.userDao.delete(user);
	}

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public User create(UserCreateDto userDto) {
    	User user = new User();
    	user.setFirstName(userDto.getFirstName());
    	user.setLastName(userDto.getLastName());
    	user.setUsername(userDto.getUsername());
    	user.setUsernameCanonical(StringFormat.canonicalize(userDto.getUsername()));
    	user.setEmail(userDto.getEmail());
    	user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
    	user.setActive(0);
    	user.setCreatedAt(LocalDateTime.now());
        user.addRole(roleDao.findByName(Role.ROLE_USER));
    	this.save(user);

		return user;
	}

    @Transactional
    public void updateUserLastLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        userDao.update(user);
    }

    @PreAuthorize("isAuthenticated()")
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByEmail(userDetails.getUsername());
    }

}
