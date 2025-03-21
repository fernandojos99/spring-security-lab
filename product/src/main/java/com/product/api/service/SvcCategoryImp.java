package com.product.api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import  com.product.common.ApiResponse;

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
		    return	new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
		    //Atrapa todas las exceptiones de la base de datos  y manda la nuestra 
		} catch (DataAccessException e) {
			// httpStatus no son solo numeros interos 
		    throw new ApiException(HttpStatus.NOT_FOUND,"Error al acceder a la base de datos");
		}
	
	}
	
	
	
	
	
	
	
	public ResponseEntity<List<Category>> getActiveCategories(){
		
		try {
		    //Atrapa todas las exceptiones de la base de datos  y manda la nuestra 
		    return new ResponseEntity<>(repo.getActiveCategories(), HttpStatus.OK);
		    
		} catch (DataAccessException e) {
			// httpStatus no son solo numeros interos 
		    throw new ApiException(HttpStatus.NOT_FOUND,"Error al acceder a la base de datos");
		}
	
	
	}
		
		

	 // Para conseguir category por Id 
	@Override
	public ResponseEntity<Category> getCategory(Integer id) {
			try {
				//validateRegionId(id);
				return new ResponseEntity<>(repo.getCategory(id), HttpStatus.OK);
			}catch (DataAccessException e) {
				//throw new DBAccessException(e);
				throw new ApiException(HttpStatus.NOT_FOUND,"Error al acceder a la base de datos");
				
			}
	}
		
	
	
		
	
	@Override	
	public ResponseEntity<ApiResponse> createCategory(DtoCategoryIn in){
		
		
		try {
			repo.createCategory(in.getCategory(), in.getTag());
	
			return new ResponseEntity<>(new ApiResponse("La category ha sido registrada"), HttpStatus.CREATED);
		} catch (DataAccessException e) {
			
			
			if (e.getLocalizedMessage().contains("categoy.category")) {
					throw new ApiException(HttpStatus.CONFLICT, "El nombre de la category ya está registrado");
			}
			if (e.getLocalizedMessage().contains("category.tag")) {
				throw new ApiException(HttpStatus.CONFLICT, "El tag de la category ya está registrado");
			} 
			
			//Aqui el codigo del profe tiene 
			//throw new DBAccessException(e);
			
			throw new ApiException(HttpStatus.NOT_FOUND,"Error al acceder a la base de datos");
				
			
			
		}
			
		
	}
		
	
	
	
	
		
		
		
		
	
	
	public ResponseEntity<ApiResponse> updateCategory(DtoCategoryIn in, Integer id){
		
		try {
			
		
			//validateCategoryId(id);  Implementar
			repo.updateCategory(id, in.getCategory(), in.getTag());
			return new ResponseEntity<>(new ApiResponse("La category ha sido actualizada"), HttpStatus.OK);
		} catch (DataAccessException e) {
					
				// httpStatus no son solo numeros interos 
			throw new ApiException(HttpStatus.NOT_FOUND,"Error al acceder a la base de datos");	
		}
	}
		

	
	public ResponseEntity<ApiResponse> enableCategory(Integer id){
		try {
			//validateRegionId(id);
			repo.enableCategory(id);
			return new ResponseEntity<>(new ApiResponse("La región ha sido activada"), HttpStatus.OK);
		}catch (DataAccessException e) {
			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND,"Error al acceder a la base de datos");
		}
		
		
	}
	
	
	public ResponseEntity<ApiResponse> disableCategory(Integer id){
		
		try {
			//validateRegionId(id);
			repo.disableCategory(id);
			return new ResponseEntity<>(new ApiResponse("La región ha sido desactivada"), HttpStatus.OK);
		}catch (DataAccessException e) {
			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND,"Error al acceder a la base de datos");
			
		}
	
		
	}
	
	
// Falta limpiar 	
//	private void validateCategoryId(Integer id) {
//		try {
//			if(repo.getCategory(id) == null) {
//				throw new ApiException(HttpStatus.NOT_FOUND, "El id de la región no existe");
//			}
//		}catch (DataAccessException e) {
//			throw new DBAccessException(e);
//		}
//	}
	

}
