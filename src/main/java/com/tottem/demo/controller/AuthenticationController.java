package com.tottem.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tottem.demo.model.AuthenticationDTO;
import com.tottem.demo.model.Cliente;
import com.tottem.demo.model.LoginResponseDTO;
import com.tottem.demo.model.RegisterDTO;
import com.tottem.demo.model.UserRole;
import com.tottem.demo.model.Usuario;
import com.tottem.demo.repository.UsuarioRepository;
import com.tottem.demo.security.TokenService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data){

        // as senhas do usuário serão armazenadas como HASH
        // assim estarão criptografadas e não podem ser diretamente acessadas
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), "default");
        
        if (data.role() != UserRole.USER) usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((Usuario)auth.getPrincipal());

            //System.out.println("AuthControllerToken: " + token);

            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (Exception e) {
            //System.out.println("Erro:  ");
            //System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }

        
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        
        // se encontra um usuário já cadastrado com o conteúdo de 'data', retorna badRequest
        if (this.usuarioRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        
        Usuario newUser;
        String encryptedPassword;
        // login é o celular
        // se o papel é USER, não é necessário utilizar senha: padrão 'default'
        switch (data.role()) {
            case USER:
                encryptedPassword = new BCryptPasswordEncoder().encode("default");
                newUser = new Cliente(data.login(), encryptedPassword, data.role());
                break;
            default:
                encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
                newUser = new Usuario(data.login(), encryptedPassword, data.role());
                break;
        }
        
        this.usuarioRepository.save(newUser);
        
        return ResponseEntity.ok().build();
    }


    // endpoint para login e registro juntos - se não existir, registra e faz login em seguida
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/loginreg")
    public ResponseEntity loginreg(@RequestBody @Valid AuthenticationDTO data){
        
        if (data.role() != UserRole.USER) return ResponseEntity.internalServerError().build();

        // se não encontra um usuário cadastrado com o conteúdo de 'data', cadastra
        if (this.usuarioRepository.findByLogin(data.login()) == null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode("default");
            Usuario newUser = new Cliente(data.login(), encryptedPassword, data.nome(), data.role());
            this.usuarioRepository.save(newUser);
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), "default");

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((Usuario)auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
