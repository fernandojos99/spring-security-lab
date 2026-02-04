package com.product.api.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.product.api.dto.in.LoginDto;
import com.product.config.JwtUtil;

/*Controlador para validar el usuario y la contraseña*/


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Productos", description = "Control de productos")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder ;
    
    //este lo hicimos en SecurityConfig
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }



    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
    	
    	//Aqui creamos un token no autenticado del usuario y la contrasenia
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
        																	loginDto.getUsername(), loginDto.getPassword());

        System.out.println("lo que recibo"+loginDto.getUsername()+loginDto.getPassword());
        //Autentificamos el usuario
        // el metodo authenticationManager va al manager y de ahi al autheticationprovider y de ahi al userdetailService
        Authentication authentication = this.authenticationManager.authenticate(login);
        //si la respuesta es negativa , ya no continua y automaticamente lanza una respuesta de que el usuario no se puede 
        //autenticar.
        
        System.out.println(authentication.isAuthenticated());
        //Para imprimir el usuario que inicio sesión.
        System.out.println(authentication.getPrincipal());

        //Creamos el JWT para enviarselo al cliente
        String jwt = this.jwtUtil.create(loginDto.getUsername());

        //Regremos un ok , sin cuerpo pero con header de Authorization , donde dice jwt es el valor del header y build 
        // es para crear la responseEntity
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }






    /**This controller shouldn't exist at production but
     * help me to get a hash by bycript
     * */
    @GetMapping("/debug/hash")
    public void printHash() {
        String hash = passwordEncoder.encode("admin123");
        System.out.println(hash);
    }



}
