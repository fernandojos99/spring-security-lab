package com.product.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name="user")
@Getter
@Setter
//@NoArgsConstructor
public class UserEntity {
	@Id
	@Column(nullable =false ,length=20)
	private String username;
	
	@Column(nullable =false ,length=200)
	private String password;
	
	@Column(length=50)
	private String email;
	
	//Para saber si la cuenta esta bloqueada 
	@Column(nullable =false ,columnDefinition = "TINYINT")
	private Boolean  locked;

	//Para saber si la cuenta esta deshabilitada
	@Column(nullable =false ,columnDefinition = "TINYINT")
	private Boolean disable;
	
	
	//fetch = FetchType.EAGER:
	//	Esto significa que cuando se cargue un usuario, se cargan también sus roles inmediatamente.
	// El resto son cosas de sql que despues se estudiaran
	@OneToMany(mappedBy = "user",fetch =FetchType.EAGER)
	private List<UserRoleEntity> roles;
	
	
	public List<UserRoleEntity> getRoles()
	{
		return roles;
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

	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public Boolean getLocked() {
	    return locked;
	}

	public void setLocked(Boolean locked) {
	    this.locked = locked;
	}

	public Boolean getDisabled() {
	    return disable;
	}

	public void setDisabled(Boolean disable) {
	    this.disable = disable;
	}

	
	
	
}




