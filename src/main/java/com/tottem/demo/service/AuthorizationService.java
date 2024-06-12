package com.tottem.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tottem.demo.repository.UsuarioRepository;

//implements UserDetailsService - Pertencente ao Spring Security
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    //Consulta dos usuários no BD pra passar para o Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return usuarioRepository.findByLogin(username);
    }
}