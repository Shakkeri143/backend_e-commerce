package com.e_commerce.backend_e_commerce.Security;

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
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
//	SECRET_KEY: A secure random key used to sign the token using the HS256 algorithm.
	private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//	EXPIRATION_TIME: Token will expire in 24 hours (in milliseconds).
	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours
	
	public String generateToken(UserDetails userDetails) {
	    Map<String, Object> claims = new HashMap<>();
	    claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
	    return createToken(claims, userDetails.getUsername());
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
	    return Jwts.builder()
	            .setClaims(claims)
	            .setSubject(subject)
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	            .signWith(SECRET_KEY)
	            .compact();
	}
	
	public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	private Claims extractAllClaims(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(SECRET_KEY)
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
	    final String email = extractEmail(token);
	    return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
