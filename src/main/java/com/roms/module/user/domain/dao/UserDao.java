package com.roms.module.user.domain.dao;

import com.roms.library.dao.GenericDaoInterface;
import com.roms.module.user.domain.model.User;

public interface UserDao extends GenericDaoInterface<User> {

    User findByEmail(String email);

}
