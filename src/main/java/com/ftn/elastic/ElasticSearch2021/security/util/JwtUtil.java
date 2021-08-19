package com.ftn.elastic.ElasticSearch2021.security.util;

import com.ftn.elastic.ElasticSearch2021.security.CustomPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("18000") //in seconds (5 hours)
    private Long expiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey("signing_key").parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public String generateToken(UserDetails userDetails) {
        CustomPrincipal principal = (CustomPrincipal) userDetails;
        Map<String, Object> claims = new HashMap<>();

        claims.put("sub", principal.getUsername());
        claims.put("id", principal.getId());
        claims.put("username", principal.getUsername());
        claims.put("firstName", principal.getFirstName());
        claims.put("lastName", principal.getLastName());
        claims.put("created", new Date(System.currentTimeMillis()));
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS256, "signing_key").compact();
    }

}
