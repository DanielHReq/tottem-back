package com.tottem.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tottem.demo.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Metodo que sera chamado quando o filtro for invocado
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtem o token da requisicao
        var token = this.recoverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);
            System.out.println(login);
            UserDetails user = usuarioRepository.findByLogin(login);
            if (user != null){
                System.out.println(user);
                // Obtem todas as informacoes usadas pelo restante dos filtros
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                // Salva no contexto da autenticacao desse usuario 
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Caso nao encontre token, nao salva nenhum contexto e continua para proximo filtro
        filterChain.doFilter(request, response);
    }
    
    private String recoverToken(HttpServletRequest request) {

        System.out.println(request);

        var authHeader = request.getHeader("Authorization");
        
        if (authHeader == null) return null;

        authHeader = authHeader.replace("Bearer ", "");

        System.out.println("authHeader:" + authHeader);
        return authHeader;
    }
}