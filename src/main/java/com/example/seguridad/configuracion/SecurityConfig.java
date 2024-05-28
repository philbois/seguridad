package com.example.seguridad.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.seguridad.jwt.JwtAutheticationFilter;

import lombok.RequiredArgsConstructor;





@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtAutheticationFilter jwtAutheticationFilter;

    @Autowired
    private AuthenticationProvider authProvider;

    @Override
    protected void configure(HttpSecurity http)throws Exception{
         http
        //desabilita token csrf
            .csrf().disable()
            // configuración de autorizacion de solicitudes
            .authorizeRequests()
                //toda ruta que este en el endp api como login que sean publicos
                .antMatchers("/api/**").permitAll()
                // cualquier otra ruta que lo autentique
                .anyRequest().authenticated()
                .and()
                // inhabilitar la politica de creacion de sesiones
                .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
                .and()
                .authenticationProvider(authProvider) 
                .addFilterBefore(jwtAutheticationFilter, UsernamePasswordAuthenticationFilter.class);
                
                
    }

}
