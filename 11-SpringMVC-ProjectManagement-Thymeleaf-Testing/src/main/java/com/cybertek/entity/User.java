package com.cybertek.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cybertek.util.Gender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private boolean enabled;
	private String phone;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public User(String firstname, String lastname, String username, String password, boolean enabled, String phone,
			Role role, Gender gender) {

		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.phone = phone;
		this.role = role;
		this.gender = gender;
	}

	public User(long id, LocalDateTime insertDateTime, String insertUserId, LocalDateTime lastUpdateDateTime,
			String lastUpdateUserId, String firstname, String lastname, String username, String password, boolean enabled,
			String phone, Role role, Gender gender) {
		super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.phone = phone;
		this.role = role;
		this.gender = gender;
	}

	public User() {

	}

}
