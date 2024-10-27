package com.onlinelibrary.service.impl;

import com.onlinelibrary.dto.JWTAuthResponse;
import com.onlinelibrary.dto.LoginDto;
import com.onlinelibrary.dto.RegisterDto;
import com.onlinelibrary.entity.Role;
import com.onlinelibrary.entity.User;
import com.onlinelibrary.exception.LibraryAPIException;
import com.onlinelibrary.repository.RoleRepository;
import com.onlinelibrary.repository.UserRepository;
import com.onlinelibrary.security.JWTTokenProvider;
import com.onlinelibrary.service.AuthService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    final private AuthenticationManager authenticationManager;

    final private UserRepository userRepository;

    final private RoleRepository roleRepository;

    final private PasswordEncoder passwordEncoder;

    final private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JWTAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        String email = loginDto.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new LibraryAPIException(HttpStatus.NOT_FOUND, "User not found"));

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .get()
                .getAuthority();

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setUserId(user.getId());

        return jwtAuthResponse;
    }




    @Override
    public String register(RegisterDto registerDto) {

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw  new LibraryAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        if (!isPasswordValid(registerDto.getPassword())) {
            throw new LibraryAPIException(HttpStatus.BAD_REQUEST, "Password must be at least 8 alphanumeric characters, contain at least one uppercase letter, and have no special characters.");
        }

        if(!isEmailValid(registerDto.getEmail())){
            throw new LibraryAPIException(HttpStatus.BAD_REQUEST, "Invalid email domain. Only accepted domains are gmail.com, hotmail.com.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }

    private boolean isPasswordValid(@NotNull String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8,}$") && !password.matches(".*[^A-Za-z0-9].*");
    }

    private boolean isEmailValid(@NotNull String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@(gmail\\.com|hotmail\\.com)$");
    }
}
