package com.example.seguridad.API;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.seguridad.DTO.usuarioDTO;
import com.example.seguridad.models.Usuario;
import com.example.seguridad.services.usuarioServices;

@RestController
@RequestMapping (value ="/api")
public class userController {

    @Autowired
    usuarioServices usuarioServices;

    @PostMapping(value="/authenticate")
    @ResponseBody
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody usuarioDTO usuarioDTO) {
     try {
 
         Usuario user = new Usuario(usuarioDTO);
         usuarioServices.guardarDatos(user);
         
         Map response = new HashMap();
         response.put("status","OK");
         response.put("code","200");
         response.put("message", "exito");
         return new ResponseEntity<>(response, HttpStatus.OK);
     }

     catch (Exception e) {
         return null;
     }
 }
}