package com.roms.module.user.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roms.library.datatype.serializer.JsonDateTimeSerializer;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="first_name")
	@Length(max = 255)
	private String firstName;
	
	@Column(name="last_name")
	@Length(max = 255)
	private String lastName;
	
	@Column(name="username", unique=true)
	@NotBlank
	@Length(max = 255)
	private String username;
	
	@Column(name="username_canonical", unique=true)
	@NotBlank
	@Length(max = 255)
	private String usernameCanonical;

	@Column(name="email", unique=true)
	@NotBlank
	@Email
	private String email;

	@Column(name="password")
	@JsonIgnore
	@NotBlank
	private String password;
	
	@Column(name="active")
	private int active;
	
	@Column(name="created_at")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	@NotNull
	private LocalDateTime createdAt;
	
	@Column(name="last_login")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @JsonSerialize(using=JsonDateTimeSerializer.class)
	private LocalDateTime lastLogin;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable( name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<Role>();

    @Column(name="token", unique=true)
    @JsonIgnore
    @Length(max = 32)
	private String token;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernameCanonical() {
		return usernameCanonical;
	}

	public void setUsernameCanonical(String usernameCanonical) {
		this.usernameCanonical = usernameCanonical;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getActive() {
		return active;
	}

    public boolean isActive() {
        return active == 1;
    }

	public void setActive(int active) {
		this.active = active;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (! this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void removeRole(Role role) {
        if (this.roles.contains(role)) {
            this.roles.remove(role);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
