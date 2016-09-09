package com.roms.module.user.service;

import com.roms.module.user.domain.dao.UserDao;
import com.roms.module.user.domain.model.Role;
import com.roms.module.user.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service("userDetailsService")
public class DefaultUserDetailService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        if (authorities.size() == 0) {
            throw new UsernameNotFoundException("User '" + user.getId() + "' has not got required authorities to login");
        }

        com.roms.library.security.userdetails.User userDetails = new com.roms.library.security.userdetails.User(
                user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
        userDetails.setUserEntity(user);
        return userDetails;
	}

    private List<GrantedAuthority> buildUserAuthority(Collection<Role> userRoles) {
        List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();

        // Build user's authorities
        for (Role userRole : userRoles) {
            userAuthorities.add(new SimpleGrantedAuthority(userRole.getName()));
        }

        return userAuthorities;
    }

}
