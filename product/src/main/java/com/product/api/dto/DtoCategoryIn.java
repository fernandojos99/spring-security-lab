package com.product.api.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;


// Es una clase que nos sirve para no exponer directamente la entity

public class DtoCategoryIn {

	// JsonProperty nos sirve para hacer referencia a una columna en especifico de la BD
	@JsonProperty("category")
	@NotNull(message="La category es obligatoria")
	private String category;     
	
	
	@JsonProperty("tag")
	@NotNull(message="El tag es obligatorio")
	private String tag;
	
	//Agregar setter and getters 
	

    
    public String getCategory(){
        return category;
    }

    
    public void setCategory(String category) {
    	this.category = category;
    }

    public String getTag(){
        return tag;
    }
    
	public void setTag(String tag) {
		this.tag = tag;
	}
	

	
}
