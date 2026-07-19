package com.minimarket.dto;
import java.util.List;
public record AuthResponse(String token,String tipo,String username,List<String> roles){}
