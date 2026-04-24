package br.uninter.medalerta.security;

import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha));

        String token = jwtUtil.gerarToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Map<String, String> dados) {
    String email = dados.get("email");
    String senha = dados.get("senha");
    String nome = dados.get("nome");
    String telefone = dados.get("telefone");
    String roleStr = dados.get("role");

    if (usuarioRepository.findByEmail(email).isPresent()) {
        return ResponseEntity.badRequest().body(Map.of("erro", "Email já cadastrado!"));
    }

    Usuario usuario = new Usuario(nome, telefone, email);
    usuario.setSenha(passwordEncoder.encode(senha));

    if (roleStr != null && roleStr.equals("ADMIN")) {
        usuario.setRole(Usuario.RoleEnum.ADMIN);
    }

    usuarioRepository.save(usuario);
    return ResponseEntity.ok(Map.of("mensagem", "Usuário cadastrado com sucesso!"));
}


    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

}