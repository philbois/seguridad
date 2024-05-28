package com.example.seguridad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.seguridad.models.AuthResponse;
import com.example.seguridad.models.LoginRequest;
import com.example.seguridad.models.RegisterRequest;
import com.example.seguridad.models.Role;
import com.example.seguridad.models.Usuario;
import com.example.seguridad.repository.usuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// este se comunica con el jwtService el cual se encarga de todo lo relacionada al token
public class AuthServiceImpl {

    @Autowired
    usuarioRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

//metodo de autenticacion
    public AuthResponse login(LoginRequest request) {
        //recibe por parametros las credenciales del usuario y contraseña ambos en nuestro request
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        //si el user se autentico correctamente se genera el token, si el user no existe se lanza excepcion
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
       Usuario user = Usuario.builder()
       // se crea el obj usuario
                .username(request.getUsername())
                .password(request.getPassword())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                //cuando se cree el usuario por primera vez va a ser del tipo usuario
                .role(Role.USER)
                .build();
// se guarda el obj en la bd
        userRepository.save(user);
// se obtiene el token a traves del servicio de jwt y se retorna al controlador quien lo va a retornar al cliente
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}
