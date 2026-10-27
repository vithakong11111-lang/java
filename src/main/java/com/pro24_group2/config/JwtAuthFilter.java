package com.pro24_group2.config;

import com.pro24_group2.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	public JwtAuthFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		// No token: carry on. SecurityConfig decides whether this path needs one (401 if it does)
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.substring(7);
		try {
			Claims claims = jwtService.parseToken(token);

			List<GrantedAuthority> authorities = new ArrayList<>();
			List<?> raw = claims.get("authorities", List.class);
			if (raw != null) {
				for (Object authority : raw) {
					authorities.add(new SimpleGrantedAuthority(authority.toString()));
				}
			}

			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (JwtException | IllegalArgumentException e) {
			// Bad signature, expired or malformed: leave the context empty, so the request gets 401
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(request, response);
	}
}