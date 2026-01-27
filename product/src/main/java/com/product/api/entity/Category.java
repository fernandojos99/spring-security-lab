package com.product.api.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;


@Entity
@Table(name = "category")
public class Category {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	// Con las siguientes 2 etiquetas se especifica mas el valor de las columnas en 
	// la base de datos .
	@JsonProperty("category_id")
	@Column(name = "category_id")
    private Integer category_id;
	
	@JsonProperty("category")
	@Column(name = "category")
	private String category;
	@JsonProperty("tag")
	@Column(name = "tag")
	private String tag;
	@JsonProperty("status")
	@Column(name = "status")
	private Integer status=1;

	// Es necesario que tenga una constructor por defecto 
    public Category(){}
    
    
    public Category(Integer id ,String category1 , String tag1,Integer status1 ){

    	
    	
        category_id=id;
        category= category1;
        tag=tag1;
        status=1;
    }

    /*Los metodos ya no son tan necesarios amenos que se utilece junto a otras 
     * tecnicas que no se abarcan en esta practica. */ 
    
    
     public String toString(){
     
        return "{"+category_id+","+category+","+tag+","+status+"}";
     }


   

    public void changeStatus(){
            status=0;
    }

    public String getcategory(){
        return category;
    }


    public String gettag(){
        return tag;
    }
    
    public Integer getcategory_id() {
    	return category_id;
    }
}

