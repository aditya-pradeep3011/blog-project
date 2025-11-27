package com.project.blog.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.project.blog.security.BlogUserDetails;
import com.project.blog.service.AuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	
	@Value("${jwt.secretKey}")
	private String secretKey;

	private final Long tokenExpiryMs = 86400000L;
	
	@Override
	public UserDetails authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		return userDetailsService.loadUserByUsername(username);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
			.addClaims(claims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + tokenExpiryMs))
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}
	
	private Key getSigningKey()
	{
		byte[] keyBytes = secretKey.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public UserDetails validateToken(String token) {
		String username = extractUsername(token);
		return userDetailsService.loadUserByUsername(username);
	}
	
	private String extractUsername(String token)
	{
		Claims claims = Jwts.parser()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claims.getSubject();
	}

}
