package com.example.seguridad.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.seguridad.models.Usuario;


public interface usuarioRepository extends CrudRepository<Usuario,Integer>{
    Optional<Usuario> findByUsername(String username);
}
