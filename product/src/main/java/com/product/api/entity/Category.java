package com.product.api.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "category")
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer category_id;
	
	private String category;
	private String tag;
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

