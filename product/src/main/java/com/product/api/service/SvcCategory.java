package com.product.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.product.api.entity.Category;

/*
 * Como buenas prácticas, para crear un servicio 
 * primero se define una interfaz que definirá los métodos que
 * ejecuta cada endpoint,
 * */
public interface SvcCategory {
	
	public ResponseEntity <List<Category>>getCategories();
	
}
