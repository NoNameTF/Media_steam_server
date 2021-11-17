package com.noname.mediasteam.controller;

import com.noname.mediasteam.config.security.annotation.CurrentUser;
import com.noname.mediasteam.config.security.UserPrincipal;
import com.noname.mediasteam.domain.user.dto.response.UserResponseDto;
import com.noname.mediasteam.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getUserById(userPrincipal.getId());
    }

}
