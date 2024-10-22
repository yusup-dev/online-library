package com.onlinelibrary.service;


import com.onlinelibrary.dto.JWTAuthResponse;
import com.onlinelibrary.dto.LoginDto;
import com.onlinelibrary.dto.RegisterDto;

public interface AuthService {

    JWTAuthResponse login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
