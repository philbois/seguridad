package com.example.seguridad.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.seguridad.DTO.usuarioDTO;

@Entity
public class Usuario {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String usuario;
    private String contraseña;

    public String getUsuario() {
        return usuario;
    }
    public String getContraseña() {
        return contraseña;
    }
    public Usuario() {
    }
    public Usuario(Integer id, String usuario, String contraseña) {
        this.id = id;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }
    public Usuario(usuarioDTO usuarioDTO) {
        this.usuario=usuarioDTO.getUsuario();
        this.contraseña=usuarioDTO.getContraseña();
    }
    
            
            


}