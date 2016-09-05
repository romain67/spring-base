package com.roms.module.user.domain.dao;

import com.roms.library.dao.GenericDaoImplementation;
import com.roms.module.user.domain.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("roleDao")
public class RoleDaoImpl extends GenericDaoImplementation<Role> implements RoleDao {

    public RoleDaoImpl() {
        super(Role.class);
    }

    public Role findByName(String name) {
        return (Role) entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.name = :name")
                .setParameter("name", name)
                .getSingleResult();
    }

}
