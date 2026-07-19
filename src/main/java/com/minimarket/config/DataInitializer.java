package com.minimarket.config;
import com.minimarket.entity.*;
import com.minimarket.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;
@Configuration
public class DataInitializer {
 @Bean CommandLineRunner seed(RolRepository rr,UsuarioRepository ur,CategoriaRepository cr,ProductoRepository pr,PasswordEncoder pe){return args->{
  if(rr.count()==0){Rol admin=rol(rr,"ROLE_ADMIN"), cajero=rol(rr,"ROLE_CAJERO"), cliente=rol(rr,"ROLE_CLIENTE"); usuario(ur,pe,"admin","Admin123!",admin);usuario(ur,pe,"cajero","Cajero123!",cajero);usuario(ur,pe,"cliente","Cliente123!",cliente);
   Categoria c=new Categoria();c.setNombre("Abarrotes");c=cr.save(c);Producto p=new Producto();p.setNombre("Arroz 1 kg");p.setPrecio(1490.0);p.setStock(40);p.setCategoria(c);pr.save(p);
  }};}
 private Rol rol(RolRepository r,String n){Rol x=new Rol();x.setNombre(n);return r.save(x);} private void usuario(UsuarioRepository r,PasswordEncoder e,String n,String p,Rol rol){Usuario u=new Usuario();u.setUsername(n);u.setPassword(e.encode(p));u.setRoles(Set.of(rol));r.save(u);}
}
