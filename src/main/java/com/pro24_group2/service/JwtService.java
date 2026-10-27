package com.pro24_group2.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

	private final SecretKey key;
	private final long expirationMinutes;

	public JwtService(@Value("${app.jwt.secret}") String secret,
	                  @Value("${app.jwt.expiration-minutes:60}") long expirationMinutes) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMinutes = expirationMinutes;
	}

	// Goal 1: create the token after login
	public String generateToken(UserDetails userDetails) {
		Instant now = Instant.now();

		List<String> authorities = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.sorted()
				.toList();

		return Jwts.builder()
				.subject(userDetails.getUsername())
				.claim("authorities", authorities)
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
				.signWith(key, Jwts.SIG.HS256)
				.compact();
	}

	// Goal 2: check the token. Throws JwtException if the signature is wrong,
	// the token is expired or malformed
	public Claims parseToken(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}