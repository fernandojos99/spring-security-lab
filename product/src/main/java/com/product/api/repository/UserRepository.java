package com.product.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.product.api.entity.UserEntity;

//JpaRepository extiende CrudRepository y agrega más funcionalidades 
// el segundo atributo es el tipo de la clave primaria
public interface UserRepository extends CrudRepository <UserEntity , String > {
	
}
