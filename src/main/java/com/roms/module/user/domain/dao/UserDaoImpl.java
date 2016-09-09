package com.roms.module.user.domain.dao;

import com.roms.library.dao.GenericDaoImplementation;
import com.roms.library.dao.UniqueValidable;
import com.roms.module.user.domain.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends GenericDaoImplementation<User>
        implements UserDao, UniqueValidable {

    public UserDaoImpl() {
        super(User.class);
    }

	public User findByEmail(String email) {
        List<User> users =  entityManager.createQuery(
                "SELECT DISTINCT u FROM User u " +
                        "LEFT JOIN FETCH u.roles r " +
                        "WHERE u.email = :email")
                .setParameter("email", email)
                .getResultList();

        return users.size() > 0 ? users.get(0) : null;
	}

    public Collection<User> findAll() {
        return entityManager.createQuery(
                "SELECT DISTINCT u FROM User u " +
                        "LEFT JOIN FETCH u.roles r")
                .getResultList();
    }

    @Override
    public boolean isUnique(String fieldName, String value) {
        Long count = (Long) entityManager.createQuery(
                "SELECT COUNT(u) FROM User u " +
                        "WHERE u."+fieldName+" = :value")
                .setParameter("value", value)
                .getResultList().get(0);
        return count == 0;
    }

}
