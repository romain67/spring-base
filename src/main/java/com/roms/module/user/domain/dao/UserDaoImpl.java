package com.roms.module.user.domain.dao;

import com.roms.library.dao.GenericDaoImplementation;
import com.roms.library.dao.UniqueValidable;
import com.roms.module.user.domain.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImplementation<User>
        implements UserDao, UniqueValidable {

    public UserDaoImpl() {
        super(User.class);
    }

	public User findByEmail(String email) {
        List<User> users = (List<User>) entityManager.createQuery(
                "SELECT DISTINCT u FROM User u " +
                        "LEFT JOIN FETCH u.roles r " +
                        "WHERE u.email = :email")
                .setParameter("email", email)
                .getResultList();

        return users.size() > 0 ? users.get(0) : null;
	}

    public User findByToken(String token) {
        List<User> users = (List<User>) entityManager.createQuery(
                "SELECT DISTINCT u FROM User u " +
                        "LEFT JOIN FETCH u.roles r " +
                        "WHERE u.token = :token")
                .setParameter("token", token)
                .getResultList();

        return users.size() > 0 ? users.get(0) : null;
    }

    public List<User> findAll() {
        return (List<User>) entityManager.createQuery(
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
