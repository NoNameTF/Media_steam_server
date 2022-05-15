package com.noname.mediasteam.security.annotation;

import com.noname.mediasteam.config.security.UserPrincipal;
import com.noname.mediasteam.domain.user.Role;
import com.noname.mediasteam.domain.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
                .id(1L)
                .email("test@test")
                .name("test")
                .password(new BCryptPasswordEncoder().encode(annotation.password()))
                .role(Role.USER)
                .build();

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal.getPassword(), userPrincipal.getAuthorities());

        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
