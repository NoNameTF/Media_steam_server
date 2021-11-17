package com.noname.mediasteam.domain.auth;

import com.noname.mediasteam.config.security.TokenProvider;
import com.noname.mediasteam.domain.auth.request.LoginRequest;
import com.noname.mediasteam.domain.auth.request.RegisterRequest;
import com.noname.mediasteam.domain.auth.response.RegisterResponse;
import com.noname.mediasteam.domain.auth.response.TokenResponse;
import com.noname.mediasteam.domain.user.Role;
import com.noname.mediasteam.domain.user.User;
import com.noname.mediasteam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public TokenResponse authentication(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        return new TokenResponse(token);
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email Address already in use.");

        User user = User.builder()
                .provider("local")
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        String location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
                .buildAndExpand(userRepository.save(user)).toUriString();

        return new RegisterResponse(location);
    }

}
