package com.minimarket.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService {
 private final String secret; private final long expiration;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){this.secret=secret;this.expiration=expiration;}
 private SecretKey key(){return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
 public String generate(UserDetails user){return Jwts.builder().subject(user.getUsername()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration)).signWith(key()).compact();}
 public String username(String token){return claim(token,Claims::getSubject);}
 public boolean valid(String token,UserDetails user){return username(token).equals(user.getUsername()) && claim(token,Claims::getExpiration).after(new Date());}
 private <T>T claim(String token,Function<Claims,T> fn){return fn.apply(Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload());}
}
