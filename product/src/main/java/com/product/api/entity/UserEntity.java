package com.product.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name="user")
//@Getter
//@Setter
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
	
}
