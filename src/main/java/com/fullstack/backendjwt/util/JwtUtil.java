package com.fullstack.backendjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtUtil {

	 private String secret = "53382f416f2f54507a75304752326c7a6f59344265413d3d0d0a";

	 	//done
	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }
	    
	    //done 
	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    
	    //done
	    private Claims extractAllClaims(String token) {
	        return Jwts
	        		.parserBuilder()
	        		.setSigningKey(getSigningKey())
	        		.build()
	        		.parseClaimsJws(token)
	        		.getBody();
	    }

	    //done
	    private SecretKey getSigningKey() {
			// TODO Auto-generated method stub
	    	byte[] keyBytes = Decoders.BASE64.decode(secret);
	        return Keys.hmacShaKeyFor(keyBytes);
		}

		private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

		//done
	    public String generateToken(String username) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, username);
	    }

	    //done
	    private String createToken(Map<String, Object> claims, String subject) {

	        return Jwts.builder()
	        		.setClaims(claims)  // currently no claims defineds as no role is assigned
	        		.setSubject(subject) // username
	        		.setIssuedAt(new Date(System.currentTimeMillis())) //token start time
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 20)) //token ended after 20 hours
	                .signWith(getSigningKey(),SignatureAlgorithm.HS256) // By Desfult using this Signature for JWT
	                .compact();
	    }

	    //done
	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	
}
