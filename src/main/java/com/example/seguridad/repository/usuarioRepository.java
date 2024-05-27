package com.example.seguridad.repository;
import org.springframework.data.repository.CrudRepository;

import com.example.seguridad.models.Usuario;


public interface usuarioRepository extends CrudRepository<Usuario, Integer> {

}
