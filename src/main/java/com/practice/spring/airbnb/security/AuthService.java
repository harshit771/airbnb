package com.practice.spring.airbnb.security;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.spring.airbnb.dto.LoginDto;
import com.practice.spring.airbnb.dto.LoginResponseDto;
import com.practice.spring.airbnb.dto.SignUpRequestDto;
import com.practice.spring.airbnb.dto.UserDto;
import com.practice.spring.airbnb.entities.User;
import com.practice.spring.airbnb.entities.enums.Role;
import com.practice.spring.airbnb.repositories.UserRepository;
import com.practice.spring.airbnb.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final SessionService sessionService;
    private final UserService userService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto){

        User user=userRepository.findByEmail(signUpRequestDto.getEmail())
                                .orElse(null);

        if(user != null ){
            throw new RuntimeException("User is exist with same email id");
        }

        User newUser=modelMapper.map(signUpRequestDto,User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser=userRepository.save(newUser);

        return modelMapper.map(newUser, UserDto.class);

    }

    public String[] login(LoginDto loginDto){
       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginDto.getEmail(),loginDto.getPassword()
        ));

        User user=(User)authentication.getPrincipal();
        String[] arr=new String[2];
        arr[0] = jwtService.generateAccessToken(user);
        arr[1] = jwtService.generateRefreshToken(user);

        return arr;

    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDto(accessToken);
    }
}
