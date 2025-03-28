package com.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface RepoProduct extends JpaRepository<Product, Integer> {
	
	
	// c y r , son una forma de representar a product  y  product_image respectivamente 
	
	@Query(value = "SELECT c.*, r.image "
			+ "FROM product c "
			+ "INNER JOIN product_image r ON r.product_id = c.product_id "
			+ "WHERE c.product_id = :product_id;", nativeQuery = true)
	DtoProductOut getProduct(Integer product_id);
	
}

