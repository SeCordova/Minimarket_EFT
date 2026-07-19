package com.minimarket.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record RegistroRequest(@NotBlank @Size(min=4,max=40) String username,@NotBlank @Size(min=8,max=100) String password){}
