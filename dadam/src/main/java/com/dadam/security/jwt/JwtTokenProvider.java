/*
 * package com.dadam.security.jwt;
 * 
 * import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.stereotype.Component;
 * 
 * import javax.crypto.SecretKey; import java.util.Date; import
 * java.util.HashMap; import java.util.Map;
 * 
 *//**
	 * JWT 토큰 제공자 - JWT 토큰 생성 - JWT 토큰 검증 - JWT 토큰에서 사용자 정보 추출
	 * 
	 * @author ERP Development Team
	 * @version 1.0
	 * @since 2025-07-11
	 */
/*
 * @Component public class JwtTokenProvider {
 * 
 * @Value("${jwt.secret:your-secret-key}") private String jwtSecret;
 * 
 * @Value("${jwt.expiration:86400000}") private long jwtExpirationMs;
 * 
 *//**
	 * JWT 토큰 생성
	 * 
	 * @param authentication 인증 정보
	 * @return JWT 토큰
	 */
/*
 * public String generateToken(Authentication authentication) { UserDetails
 * userDetails = (UserDetails) authentication.getPrincipal();
 * 
 * Date now = new Date(); Date expiryDate = new Date(now.getTime() +
 * jwtExpirationMs);
 * 
 * Map<String, Object> claims = new HashMap<>(); claims.put("username",
 * userDetails.getUsername()); claims.put("authorities",
 * userDetails.getAuthorities());
 * 
 * return Jwts.builder() .setSubject(userDetails.getUsername())
 * .setClaims(claims) .setIssuedAt(now) .setExpiration(expiryDate)
 * .signWith(getSigningKey(), SignatureAlgorithm.HS512) .compact(); }
 * 
 *//**
	 * JWT 토큰에서 사용자명 추출
	 * 
	 * @param token JWT 토큰
	 * @return 사용자명
	 */
/*
 * public String getUsernameFromToken(String token) { Claims claims =
 * Jwts.parserBuilder() .setSigningKey(getSigningKey()) .build()
 * .parseClaimsJws(token) .getBody();
 * 
 * return claims.getSubject(); }
 * 
 *//**
	 * JWT 토큰 유효성 검증
	 * 
	 * @param token JWT 토큰
	 * @return 유효성 여부
	 */
/*
 * public boolean validateToken(String token) { try { Jwts.parserBuilder()
 * .setSigningKey(getSigningKey()) .build() .parseClaimsJws(token); return true;
 * } catch (JwtException | IllegalArgumentException e) { return false; } }
 * 
 *//**
	 * JWT 토큰에서 클레임 추출
	 * 
	 * @param token JWT 토큰
	 * @return 클레임
	 */
/*
 * public Claims getClaimsFromToken(String token) { return Jwts.parserBuilder()
 * .setSigningKey(getSigningKey()) .build() .parseClaimsJws(token) .getBody(); }
 * 
 *//**
	 * 서명 키 생성
	 * 
	 * @return SecretKey
	 */
/*
 * private SecretKey getSigningKey() { return
 * Keys.hmacShaKeyFor(jwtSecret.getBytes()); }
 * 
 *//**
	 * 토큰 만료 시간 확인
	 * 
	 * @param token JWT 토큰
	 * @return 만료 여부
	 */
/*
 * public boolean isTokenExpired(String token) { try { Claims claims =
 * getClaimsFromToken(token); Date expiration = claims.getExpiration(); return
 * expiration.before(new Date()); } catch (Exception e) { return true; } }
 * 
 *//**
	 * 토큰에서 회사 ID 추출
	 * 
	 * @param token JWT 토큰
	 * @return 회사 ID
	 */
/*
 * public String getComIdFromToken(String token) { try { Claims claims =
 * getClaimsFromToken(token); return claims.get("comId", String.class); } catch
 * (Exception e) { return null; } }
 * 
 *//**
	 * 토큰에서 권한 정보 추출
	 * 
	 * @param token JWT 토큰
	 * @return 권한 정보
	 *//*
		 * public String getAuthorityFromToken(String token) { try { Claims claims =
		 * getClaimsFromToken(token); return claims.get("authority", String.class); }
		 * catch (Exception e) { return null; } } }
		 */