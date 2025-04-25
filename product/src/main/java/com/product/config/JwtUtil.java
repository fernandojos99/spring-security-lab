package com.product.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;


//Para despues poderla inyectar 
@Component
public class JwtUtil {
    private static String SECRET_KEY = "fernando";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    
    //Recibimos un usuario y creamos el JWT para ese usuario
    public String create(String username) {
        return JWT.create()
        		// el asunto sera el usurio en cuestion
                .withSubject(username)  
             // Quien crea este Jwt
                .withIssuer("platzi-pizza") 
             // fecha que see crea 
                .withIssuedAt(new Date())   
              //Cuando expira en este caso va a durar 15 dias ,suma 15 dias mas la actual
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15))) 
             // Para firmar nuestro token , este recibe algo de la clase ALGORITM para eso se crea arriba
				//Dice que el HMAC256 es muy popular a esto le agregamos nuestra palabra clave.														
                .sign(ALGORITHM);   
    }
    
    
    // Es para validar si un token es valido 
    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt);
            return true;
            // se tiene que poner dentro de un catch porque si no es valido JWT lanza esa exception
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    
    // Metodo util para obtener el usuario al cual pertenece el JWt
    public String getUsername(String jwt) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
                //el verify devuelve los parametros mas legibles
                .getSubject();
    }
    
    
    
}