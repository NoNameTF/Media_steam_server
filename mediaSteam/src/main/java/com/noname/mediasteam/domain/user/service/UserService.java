package com.noname.mediasteam.domain.user.service;

import com.noname.mediasteam.config.security.UserPrincipal;
import com.noname.mediasteam.domain.user.User;
import com.noname.mediasteam.domain.user.dto.response.UserResponseDto;
import com.noname.mediasteam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserResponseDto getUserById(Long id) {
        return User.of(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 요청")));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserPrincipal.create(user);
    }
}
