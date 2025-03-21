package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Category;

import jakarta.transaction.Transactional;

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
	
	/*Listar las categorias activas */
	@Query(value ="SELECT * FROM category WHERE status = 1 ORDER BY category", nativeQuery = true)
	List<Category> getActiveCategories();
	
	// Esta es otra forma de hacer el metodo de arriba 
	//List<Region> findByStatusOrderByRegion(@Param("status") Integer status);
	
	
	  
	  // Es para obtener categoria por ID 
	@Query(value = "SELECT * FROM category WHERE category_id = :category_id ORDER BY category", nativeQuery = true)
	Category getCategory(@Param("category_id") Integer category_id);
	
	
	/*Para agregar una nueva categoria */
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO category(category, tag, status) VALUES (:category, :tag, 1)", nativeQuery = true)
	void createCategory(String category, String tag);
	
	
	
	
	/*Para actualizar la categoria*/
	@Modifying
	@Transactional
	@Query(value ="UPDATE category SET category = :category, tag = :tag WHERE category_id = :category_id",nativeQuery = true)    
	
	void updateCategory(@Param("category_id") Integer category_id, @Param("category") String category,@Param("tag") String tag);
	
	
	
	
	/*método que activa y desactiva una category.*/
	@Modifying
	@Transactional
	@Query(value ="UPDATE category SET status = :status WHERE category_id = :category_id", nativeQuery = true)
	void updateCategoryStatus(@Param("category_id") Integer category_id, @Param("status") Integer status);
	
	
	
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE category SET status = 1 WHERE category_id = :category_id;", nativeQuery = true)
	void enableCategory(@Param("category_id") Integer category_id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE category SET status = 0 WHERE category_id = :category_id;", nativeQuery = true)
	void disableCategory(@Param("category_id") Integer category_id);
	
	
	
}
