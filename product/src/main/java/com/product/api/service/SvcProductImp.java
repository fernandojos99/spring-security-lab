package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.common.dto.ApiResponse;
import com.product.common.mapper.MapperProduct;
import com.product.exception.ApiException;
//import com.product.exception.DBAccessException;



@Service
public class SvcProductImp implements SvcProduct{
	
	
	//Autowired para inyectar dependencias
	@Autowired
	RepoProduct repo;
	@Autowired
	RepoProductImage repoProductImage;
	
	@Autowired
	MapperProduct mapper;
	
	
	@Value("${app.upload.dir}")
	private String uploadDir;

	@Override
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		try {
			List<Product> products = repo.findAll();
			return new ResponseEntity<>(mapper.fromProductList(products), HttpStatus.OK);
		}catch (DataAccessException e) {
			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
			
		}
	}

	//punto 3 del pdf11
	@Override
	public ResponseEntity<DtoProductOut> getProduct(Integer id) {
		try {
			//validateProductId(id);
			
			DtoProductOut product = repo.getProduct(id);
			if(product == null )
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del cliente no existe");
			
			String image = readProductImageFile(id);
			product.setImage(image);
			
			return new ResponseEntity<>(product, HttpStatus.OK);
			
			
		//Esto se enviaba al inicio de la practica
		//	return new ResponseEntity<>(null, HttpStatus.OK);
		}catch (DataAccessException e) {
			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> createProduct(DtoProductIn in) {
		try {
			
			
			Product product = mapper.fromDto(in);
			repo.save(product);
			
			
			//Se va a crear una instancia de ProductImage , 
			//para crear un registro de cuando se cree un producto
			
			
			ProductImage productImage = new ProductImage();
			productImage.setProduct_id(product.getProduct_id());
			productImage.setImage("");
			productImage.setStatus(1);
			repoProductImage.save(productImage);
			
			
			return new ResponseEntity<>(new ApiResponse("El producto ha sido registrado"), HttpStatus.CREATED);
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");

			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> updateProduct(Integer id, DtoProductIn in) {
		try {
			validateProductId(id);
			Product product = mapper.fromDto(id, in);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido actualizado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");

			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> enableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(1);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido activado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> disableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(0);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido desactivado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
		}
	}
	
	private void validateProductId(Integer id) {
		try {
			if(repo.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
			}
		}catch (DataAccessException e) {
			//throw new DBAccessException(e);
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
		}
	}
	
	
	
	
	private String readProductImageFile(Integer product_id) {
	    try {
		ProductImage productImage = repoProductImage.findByProduct_id(product_id);
		if(productImage == null)
			return "";
		
		String imageUrl = productImage.getImage();
		
		// Si la URL comienza con "/" la eliminamos para obtener la ruta relativa
	  	 if (imageUrl.startsWith("/")) {
	       	    imageUrl = imageUrl.substring(1);
	   	}
	  
	  	 // Construir el Path
	  	 Path imagePath = Paths.get(uploadDir, imageUrl);
	  
	  	 // Verifica que el archivo exista
	   	if (!Files.exists(imagePath))
	   	    return "";
	  
	// Leer los bytes de la imagen y codificarlos a Base64
	byte[] imageBytes = Files.readAllBytes(imagePath);
	return Base64.getEncoder().encodeToString(imageBytes);
	    
	    }catch (DataAccessException e) {
	    	//throw new DBAccessException(e);
	    	throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
	    }catch (IOException e) {
	    	throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
	    }
	}
	
	
	
	
	
	
	

}
