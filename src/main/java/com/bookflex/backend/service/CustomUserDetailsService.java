package com.bookflex.backend.service;

import com.bookflex.backend.dto.UserDto;
import com.bookflex.backend.exception.UserNotFoundException;
import com.bookflex.backend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userMapper.findByUserId(Long.valueOf(userId))
                .map(user -> addAuthorities(user))
                .orElseThrow(() -> new UserNotFoundException(userId + "> 찾을 수 없습니다."));
    }

    private UserDto addAuthorities(UserDto userDto) {
        userDto.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(userDto.getRole())));

        return userDto;
    }
}
