package com.example.jwtAuthentication.filters;

import com.example.jwtAuthentication.entity.AppUser;
import com.example.jwtAuthentication.repository.UserRepository;
import com.example.jwtAuthentication.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) filterChain.doFilter(request,response);
        if (authorization.startsWith("Bearer ")) filterChain.doFilter(request,response);

        String jwt_token = authorization.split(" ")[1];
        String username = jwtService.getUsername(jwt_token);
//        log.info("username: {}", username);
        System.out.println("username: "+username);

        if (username == null) filterChain.doFilter(request,response);

        Optional<AppUser> userData = userRepository.findByUsername(username);/*.orElse(null)*/
        //filterChain.doFilter(request,response);

        if (SecurityContextHolder.getContext().getAuthentication() != null) filterChain.doFilter(request,response);

        UserDetails userDetails = User.builder()
                .username(userData.get().getUsername())
                .password(userData.get().getPassword())
                .build();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,
                userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

         SecurityContextHolder.getContext().setAuthentication(token);
        System.out.println(authorization);
        System.out.println(jwt_token);

        filterChain.doFilter(request, response);
    }
}
