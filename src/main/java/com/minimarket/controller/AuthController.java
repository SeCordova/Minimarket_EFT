package com.minimarket.controller;
import com.minimarket.dto.*;
import com.minimarket.entity.Rol;
import com.minimarket.entity.Usuario;
import com.minimarket.repository.RolRepository;
import com.minimarket.repository.UsuarioRepository;
import com.minimarket.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Set;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final AuthenticationManager auth; private final JwtService jwt; private final UsuarioRepository usuarios; private final RolRepository roles; private final PasswordEncoder encoder;
 public AuthController(AuthenticationManager auth,JwtService jwt,UsuarioRepository usuarios,RolRepository roles,PasswordEncoder encoder){this.auth=auth;this.jwt=jwt;this.usuarios=usuarios;this.roles=roles;this.encoder=encoder;}
 @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest req){Authentication a=auth.authenticate(new UsernamePasswordAuthenticationToken(req.username(),req.password()));var u=(org.springframework.security.core.userdetails.UserDetails)a.getPrincipal();return new AuthResponse(jwt.generate(u),"Bearer",u.getUsername(),u.getAuthorities().stream().map(x->x.getAuthority()).toList());}
 @PostMapping("/registro") @ResponseStatus(HttpStatus.CREATED) public Usuario registro(@Valid @RequestBody RegistroRequest req){if(usuarios.findByUsername(req.username()).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"El usuario ya existe");Rol rol=roles.findAll().stream().filter(r->r.getNombre().equals("ROLE_CLIENTE")).findFirst().orElseThrow();Usuario u=new Usuario();u.setUsername(req.username());u.setPassword(encoder.encode(req.password()));u.setRoles(Set.of(rol));return usuarios.save(u);}
}
