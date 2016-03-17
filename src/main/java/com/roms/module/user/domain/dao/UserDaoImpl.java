package com.roms.module.user.domain.dao;

import com.roms.library.dao.GenericDaoImplementation;
import com.roms.module.user.domain.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends GenericDaoImplementation<User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

	public User findByEmail(String email) {
        return (User) entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email)
                .getSingleResult();
	}

}
