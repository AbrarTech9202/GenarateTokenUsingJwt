package com.jwt.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
		
	String requestheader = request.getHeader("Authorization");
	log.info("Header :{}"+requestheader);
	
	//create Bearer token
	
	String username=null;
	String token=null;
	
	if(requestheader !=null && requestheader.startsWith("Bearer"))
	{
		   token = requestheader.substring(7);
		   
		   try {
               username = this.jwtHelper.getUsernameFromToken(token);
               
           } catch (IllegalArgumentException e) {
               logger.info("Illegal Argument while fetching the username !!");
               e.printStackTrace();
           } catch (ExpiredJwtException e) {
               logger.info("Given jwt token is expired !!");
               e.printStackTrace();
           } catch (MalformedJwtException e) {
               logger.info("Some changed has done in token !! Invalid Token");
               e.printStackTrace();
           } catch (Exception e) {
               e.printStackTrace();
           }
       } else {
           logger.info("Invalid Header Value !! ");
       }
	
	if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
	{
		UserDetails useeDetails = userDetailsService.loadUserByUsername(username);
	    Boolean validateToken = jwtHelper.validateToken(token, useeDetails);
	    
	    if(validateToken)
	    {
	    	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(useeDetails,useeDetails.getAuthorities(), null);
	    	authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
	    	
	    	SecurityContextHolder.getContext().setAuthentication(authentication);
	    }
	    else {
			log.info("vaidation fail !!");
		}
	}
	
	 filterChain.doFilter(request, response);
	}
	
}
