package com.roms.module.user.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roms.library.datatype.serializer.JsonDateTimeSerializer;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue
	private long id;
	
	@Column(name="firstname")
	@Length(max = 255)
	private String firstname;
	
	@Column(name="lastname")
	@Length(max = 255)
	private String lastname;
	
	@Column(name="username")
	@NotBlank
	@Length(max = 255)
	private String username;
	
	@Column(name="username_canonical")
	@NotBlank
	@Length(max = 255)
	private String usernameCanonical;

	@Column(name="email")
	@NotBlank
	@Email
	private String email;
	
	@Column(name="salt")
	@JsonIgnore
	@NotBlank
	private String salt;
	
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
	private LocalDateTime lastLogin;
	
	@Column(name="role")
	@NotEmpty
	private String role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
