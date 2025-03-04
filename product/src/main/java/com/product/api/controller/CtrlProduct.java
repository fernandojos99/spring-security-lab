package com.product.api.controller;
import com.product.Almacen;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import com.product.api.service.SvcCategoryImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/category")
public class CtrlProduct {
	
	
	@Autowired
	SvcCategory svc;
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategories() {
		
		return svc.getCategories();
	}
	

}
