package com.product.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.product.api.entity.UserEntity;
import com.product.api.entity.UserRoleEntity;
import com.product.api.repository.UserRepository;
/*Esta clase es la creadora de usuarios apartir de usar un repositorio para buscarlos en la BD 
 * 
 *  
 *  IMPLEMENTAMOS EL UserDetailService*/


//Para que entre al ciclo de vida de spring 
@Service
public class UserSecurityService implements UserDetailsService {
	private final UserRepository userRepository;
	
	@Autowired
	
	public UserSecurityService (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    UserEntity userEntity = this.userRepository.findById(username)
	        .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));

	    System.out.print(userEntity);
	    
	    
	    //String de roles
	    String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);
	    
	    
	    return User.builder()
	        .username(userEntity.getUsername())
	        .password(userEntity.getPassword())
	        //.roles(roles)
	        .authorities(this.grantedAuthorities(roles))
	        .accountLocked(userEntity.getLocked())
	        .disabled(userEntity.getDisabled())
	        .build();
	}


	
	private String[] getAuthorities(String role) {
	        if ("ADMIN".equals(role) || "CUSTOMER".equals(role)) {
	            return new String[] {"random_order"};
	        }

	        return new String[] {};
	    }

	
	
    private List<GrantedAuthority> grantedAuthorities(String[] roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for (String role: roles) {
        	// con esto agregamos todos los roles mas el prefijo a authorities asi como se hacia en la clase interna 
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            // En resumen agrega otra authority en base al role que se este leyendo
            //Se recibe el role se pasa el metodo de arriab y se verifica si admin o customer y se le da la authority 
            for (String authority: this.getAuthorities(role)) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        return authorities;
    }

	
	
	
	
	
}
