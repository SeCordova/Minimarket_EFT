package com.minimarket;
import com.minimarket.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import static org.junit.jupiter.api.Assertions.*;
class JwtServiceTest {
 @Test void debeGenerarYValidarToken(){JwtService jwt=new JwtService("MiniMarketPlusClaveJwtSegura2026ConMasDeTreintaYDosCaracteres",60000);var user=User.withUsername("admin").password("x").roles("ADMIN").build();String token=jwt.generate(user);assertEquals("admin",jwt.username(token));assertTrue(jwt.valid(token,user));}
}
