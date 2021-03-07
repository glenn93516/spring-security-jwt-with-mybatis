package com.bookflex.backend.controller;

import com.bookflex.backend.dto.LoginDto;
import com.bookflex.backend.dto.UserDto;
import com.bookflex.backend.exception.LoginFailedException;
import com.bookflex.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.join(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        ResponseEntity responseEntity = null;
        try {
            String token = userService.login(loginDto);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + token);

            return new ResponseEntity(token, httpHeaders, HttpStatus.OK);
        } catch (LoginFailedException exception) {
            logger.debug(exception.getMessage());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity findUserByUsername(final Authentication authentication) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        String username = authentication.getName();
//
//        return ResponseEntity.ok(userService.findUserByUsername(username));

        Long userId = ((UserDto) authentication.getPrincipal()).getUserId();

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.findByUserId(userId));
    }
}
