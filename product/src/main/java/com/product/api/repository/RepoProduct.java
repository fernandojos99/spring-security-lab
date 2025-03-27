package com.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface RepoProduct extends JpaRepository<Product, Integer> {
	
}

