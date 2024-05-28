package com.example.seguridad.services;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;




@Service
public class JwtService {

    private static final String SECRET_KEY="your_secret_key";

    public String getToken(UserDetails user) {
       return getToken(new HashMap<>(),user);
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails user){
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            //fecha en q se crea la del sistema
            .setIssuedAt(new Date(System.currentTimeMillis()))
            //fecha de expiracion la del sistema sumandole un dia
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public  String extractUsername(String token) {
        return getClaim(token, Claims::getSubject); 
        /*  Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();*/
    }

    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJwt(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        //se crean los claims los obtenemos a todos
        final Claims claims = getAllClaims(token);
        //luego aplicamos la funcion y retornamos resultado
        return claimsResolver.apply(claims);

    }
//devuelve fecha de expiracion
    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration); 
    }
//ver si el token expiro
    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
}
