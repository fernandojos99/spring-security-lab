package com.product;

import com.product.api.entity.Category;

import java.util.ArrayList;
import java.util.List;


public class Almacen {

    List<Category> categories = new ArrayList<>();
    
    // Para llevar conteo del id
    Integer ids=1;
    public void createCategory(String category, String tag){
        // Validar que no existe en la lista 
        if (checkValid(category,tag)){
            categories.add(new Category(ids,category,tag,1));
            ids++;
        }
        else{
            System.out.println("Losiento los parametros ya existen en la lista ");
        }
       
    }

    // Checa si hay repitidos en caso de repetidos regresa un false
    public boolean checkValid(String category, String tag){
        for (Category cat : categories) {
            if(cat.getcategory().equals(category) || cat.gettag().equals(tag)  ){
                return false;
            }    
        }
        return true;
    }


    // Tiene que regresar una lista de categorias
    public List<Category> getCategories(){
        if (categories.isEmpty()) {
            // regresa lista vacia 
            List<Category> cateReturn2 = new ArrayList<>();

            return cateReturn2;
       }else{
            List<Category> cateReturn = new ArrayList<>();
            for (Category cat : categories) {
            cateReturn.add(cat);

            }
            return cateReturn;
       }
    }


    //Busca la categoria y cambia su estatus 
    public void deleteCategory(Integer id){
        System.out.println("entro almenos al metodo ");
        for (Category cat : categories) {
            if(cat.getcategory_id()==id){
                System.err.println("es igual");
                cat.changeStatus();
            }
            else{
                System.out.println("no es igual ");
            }

        }
    }


}

