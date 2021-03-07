package com.bookflex.backend.mapper;

import com.bookflex.backend.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<UserDto> findUserByUsername(String username);
    Optional<UserDto> findByUserId(Long userId);
    void save(UserDto userDto);
}
