package com.example.seguridad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.seguridad.models.Usuario;
import com.example.seguridad.repository.usuarioRepository;
@Service
public class usuarioServicesImpl implements usuarioServices {
    @Autowired
    usuarioRepository usuarioRepository;

    @Override
    public void guardarDatos(Usuario user){
        usuarioRepository.save(user);
    }


}