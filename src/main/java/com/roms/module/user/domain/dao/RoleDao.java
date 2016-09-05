package com.roms.module.user.domain.dao;

import com.roms.library.dao.GenericDaoInterface;
import com.roms.module.user.domain.model.Role;

public interface RoleDao extends GenericDaoInterface<Role> {

    Role findByName(String name);

}
