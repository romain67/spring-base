package com.roms.library.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

/**
 * Created by romain on 04/07/2016.
 */
public class User extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 5985765322810028590L;

    /**
     * Read only user entity
     */
    private com.roms.module.user.domain.model.User userEntity;

    public User(String username, String password, boolean enabled,
                boolean accountNonExpired, boolean credentialsNonExpired,
                boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public com.roms.module.user.domain.model.User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(com.roms.module.user.domain.model.User userEntity) {
        this.userEntity = userEntity;
    }
}
