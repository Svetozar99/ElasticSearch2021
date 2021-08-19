package com.ftn.elastic.ElasticSearch2021.security.filter;

import com.ftn.elastic.ElasticSearch2021.security.util.JwtUtil;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("Authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            filterChain.doFilter(servletRequest, servletResponse);
        } else {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);

            System.out.println(token);

            if (new JwtUtil().isTokenExpired(token)){
                throw new ServletException("Token expired!");
            }

            try {
                final Claims claims = Jwts.parser().setSigningKey("signing_key").parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid token");
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
