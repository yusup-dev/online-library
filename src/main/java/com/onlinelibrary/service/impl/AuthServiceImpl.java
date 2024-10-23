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
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()
                )
        );

        // Set authentication in the context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(authentication);

        // Get the role from the Authentication object
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .get()
                .getAuthority();

        // Return both the token and the role
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(role);  // Set the role

        return jwtAuthResponse;
    }



    @Override
    public String register(RegisterDto registerDto) {

        // add check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw  new LibraryAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
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
}
