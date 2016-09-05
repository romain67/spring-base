package com.roms.module.user.service;

import com.roms.module.user.domain.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountService {
	
	@Autowired
	private UserDao userDao;

}
