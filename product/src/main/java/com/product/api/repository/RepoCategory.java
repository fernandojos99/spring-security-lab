package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Category;

/*
 * Dentro de esta interfaz, definiremos 
 * la firma de los métodos que hacen queries.
 * 
 */

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {
	
	//Para que use directamente codigo sql : nativeQuery=true
	@Query(value ="SELECT * FROM category ", nativeQuery = true)
	List<Category> getCategories();
	
}
