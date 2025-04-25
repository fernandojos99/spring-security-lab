package com.product.api.dto.in;

import lombok.Data;


// Le puso @Data porque es un DTO plano
@Data 
public class LoginDto {
    private String username;
    private String password;

    public String getUsername() {
    	return username;
    }
    
    public String getPassword(){
    	return password;
    }

}

