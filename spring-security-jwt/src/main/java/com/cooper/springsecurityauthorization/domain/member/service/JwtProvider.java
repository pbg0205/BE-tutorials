package com.cooper.springsecurityauthorization.domain.member.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final SecretKey accessTokenPrivateKey;
	private final Long accessTokenExpirationMillis;
	private final JwtParser jwtParser;

	public JwtProvider(
		@Value("${jwt.access-token.private-key}") final String accessTokenPrivateKey,
		@Value("${jwt.access-token.expire-millis}") final Long accessTokenExpirationMillis
	) {
		this.accessTokenPrivateKey = transformSecretKey(accessTokenPrivateKey);
		this.accessTokenExpirationMillis = accessTokenExpirationMillis;
		this.jwtParser = createJwtParser(this.accessTokenPrivateKey);
	}

	private JwtParser createJwtParser(final SecretKey accessTokenPrivateKey) {
		return Jwts.parser()
			.verifyWith(accessTokenPrivateKey)
			.build();
	}

	private SecretKey transformSecretKey(final String accessTokenPrivateKey) {
		return Keys.hmacShaKeyFor(accessTokenPrivateKey.getBytes(StandardCharsets.UTF_8));
	}

	public String createAccessToken(final Long memberId, final String email, final String role) {
		final Date nowDate = new Date();
		final Date expirationDate = new Date(nowDate.getTime() + accessTokenExpirationMillis);
		final Map<String, Object> claims = new HashMap<>();

		claims.put("memberId", memberId);
		claims.put("email", email);
		claims.put("role", role);

		return Jwts.builder()
			.subject(String.valueOf(memberId))
			.claims(claims)
			.issuedAt(nowDate)
			.expiration(expirationDate)
			.signWith(accessTokenPrivateKey)
			.compact();
	}

	public Claims extractAccessTokenPayload(final String accessToken) {
		return this.jwtParser
			.parseSignedClaims(accessToken)
			.getPayload();
	}
}
