package com.example.seguridad.API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/sec")
@RequiredArgsConstructor
public class TController {

    @PostMapping(value="/tcont")
    public String welcome(){
        return "welcome form secure endpoint";
    }

}
