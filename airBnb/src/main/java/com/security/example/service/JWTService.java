package com.security.example.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.example.entity.PropertyUser;

import jakarta.annotation.PostConstruct;

@Service
public class JWTService {

	@Value("${jwt.algorithms.key}")
	private String algorithmKey;
	@Value("${jwt.issuer}")
	private String issuer;

	@Value("${jwt.expiry.duration}")
	private int expiryTime;

	private Algorithm algorithm;
	private final static String USER_NAME = "username";

	@PostConstruct
	public void postConstruct() {
		algorithm = Algorithm.HMAC256(algorithmKey);
	}

	public String generateToken(PropertyUser propertyUser) {
		return JWT.create().withClaim("USER_NAME", propertyUser.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + expiryTime)).withIssuer(issuer).sign(algorithm);

	}

	public String getUsername(String token) {
		DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
		String username = decodedJWT.getClaim(USER_NAME).asString();
		return username;
	}
}
