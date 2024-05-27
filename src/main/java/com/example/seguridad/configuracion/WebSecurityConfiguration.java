package com.example.seguridad.configuracion;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth
            .inMemoryAuthentication()
            .withUser("usuario").password(passwordEncoder().encode("contraseña")).roles("USER");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/api/status/**").permitAll()
                .antMatchers("/api/authenticate/**").permitAll()
                //.antMatchers("api/intruction/*.hasRole("INTRUCCION")/")
                .and().csrf().disable();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Permite solicitudes desde este origen
        config.addAllowedMethod("*"); // Permite todos los métodos
        config.addAllowedHeader("*"); // Permite todos los encabezados
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}