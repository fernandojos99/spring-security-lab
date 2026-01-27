package com.product.api.controller;
import com.product.Almacen;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.dto.in.DtoProductIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import com.product.api.service.SvcCategoryImp;
import com.product.api.service.SvcProduct;
import com.product.common.dto.ApiResponse;
import com.product.exception.ApiException;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




//http://localhost:8080/category
@RestController
@RequestMapping("/category")

//Le cambie el nombre de CtrlProduct a CtrlCategory
public class CtrlCategory {
	
	
	private final SvcCategory svc;
	private final SvcProduct svc2;
	
	
	@Autowired
	public  CtrlCategory (SvcCategory svc,SvcProduct svc2) {
		this.svc = svc;
		this.svc2 = svc2;
		
	}
	
	
	//Aqui platzi hace un contructor 
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategories() {
		
		return svc.getCategories();
	}
	
	
	@GetMapping("/active")
	public ResponseEntity<List<Category>> getActiveCategorites() {
		return svc.getActiveCategories();
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
		return svc.getCategory(id);
	}

	
	
	
	// Para poner aprueba los siguiente endpoint requiero usar Postman o ThunderClient de VS
	// La forma de pasarle los parametros es mediante JSON 
	// Por ejemplo 
	/*
	 * {
    	"category": "Tecnologíaaaaa",
    	"tag": "Gadgetsssssss"
		}
	 * */
	
	@PostMapping
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody DtoCategoryIn in, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc.createCategory(in);
	}
	
	
	// En base a un id modemos modificar los valores de category
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @Valid @RequestBody DtoCategoryIn in,BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc.updateCategory(in,id);
	}
	
	
	
	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disableCategory(@PathVariable Integer id) {
		return svc.disableCategory(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//Este metodo es del CtrlProduct
	@PostMapping("/extra")
	public ResponseEntity<ApiResponse> createProduct_Category(@Valid @RequestBody DtoProductIn in, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc2.createProduct(in);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
