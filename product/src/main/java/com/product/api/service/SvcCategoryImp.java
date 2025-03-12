package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;

@Service
public class SvcCategoryImp implements SvcCategory{

	//Que esto se ocupa para hacer la inyeccion de dependencias.
	@Autowired
	RepoCategory repo;
	
	
	/*
	 * El método lo que hace es retornar directamente
	 *  lo que devuelve el método getCategories del
	 * repositorio.
	 * */
	@Override
	public ResponseEntity <List<Category>> getCategories() {
	
		
		try {
		    //Atrapa todas las exceptiones de la base de datos  y manda la nuestra 
		    return	new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
		} catch (DataAccessException e) {
			// httpStatus no son solo numeros interos 
		    throw new ApiException(HttpStatus.NOT_FOUND,"Error al acceder a la base de datos");
		}
	
	}	

}
