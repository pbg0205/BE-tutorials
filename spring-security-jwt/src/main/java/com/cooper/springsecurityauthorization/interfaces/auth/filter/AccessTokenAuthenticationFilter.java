package com.cooper.springsecurityauthorization.interfaces.auth.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cooper.springsecurityauthorization.domain.member.service.JwtProvider;
import com.cooper.springsecurityauthorization.domain.member.model.MemberRole;
import com.cooper.springsecurityauthorization.interfaces.auth.dto.token.CustomUserDetails;

public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

	private static final String BEARER_TYPE = "Bearer";
	private static final String AUTHORIZATION_DELIMITER = " ";

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
		final FilterChain filterChain) throws ServletException, IOException {

		ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

		final String accessToken = extractAccessToken(request.getHeader(HttpHeaders.AUTHORIZATION));

		final AbstractAuthenticationToken token = createAuthenticationToken(accessToken);

		SecurityContextHolder.getContext().setAuthentication(token);

		filterChain.doFilter(wrappingRequest, wrappingResponse);
		wrappingResponse.copyBodyToResponse();
	}

	private String extractAccessToken(final String authorizationHeader) {
		if (authorizationHeader == null || authorizationHeader.isEmpty()) {
			return "";
		}

		final String[] split = authorizationHeader.split(AUTHORIZATION_DELIMITER);

		if (!BEARER_TYPE.equalsIgnoreCase(split[0])) {
			throw new IllegalArgumentException("authorization header type is not supported");
		}


		if (split.length != 2) {
			throw new IllegalArgumentException("authorization header is invalid header");
		}

		return split[1];
	}

	private AbstractAuthenticationToken createAuthenticationToken(final String accessToken) {
		try {
			return createAuthenticationTokenFromAccessToken(accessToken);
		} catch (IllegalArgumentException exception) {
			return createAnonymousAccessToken();
		}
	}

	private UsernamePasswordAuthenticationToken createAuthenticationTokenFromAccessToken(final String accessToken) {
		if (StringUtils.isBlank(accessToken)) {
			throw new IllegalArgumentException("access token is empty");
		}

		final Claims claims = jwtProvider.extractAccessTokenPayload(accessToken);
		final Integer memberId = claims.get("memberId", Integer.class);
		final String email = claims.get("email", String.class);
		final String role = claims.get("role", String.class);
		final MemberRole authorRole = MemberRole.of(role);

		final CustomUserDetails customUserDetails =
			new CustomUserDetails(memberId, email, List.of(new SimpleGrantedAuthority(authorRole.getRoleName())));

		return new UsernamePasswordAuthenticationToken(email, "", customUserDetails.getAuthorities());
	}

	private AnonymousAuthenticationToken createAnonymousAccessToken() {
		return new AnonymousAuthenticationToken("anonymous", "anonymous",
			List.of(new SimpleGrantedAuthority(MemberRole.ANONYMOUS.getRoleName())));
	}
}
