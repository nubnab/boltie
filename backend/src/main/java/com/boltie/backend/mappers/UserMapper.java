package com.boltie.backend.mappers;

import com.boltie.backend.dto.UserDto;
import com.boltie.backend.dto.UserRecordingsDto;
import com.boltie.backend.dto.UserRoleDto;
import com.boltie.backend.dto.UsernameDto;
import com.boltie.backend.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    UserRecordingsDto toUserRecordingsDto(User user);

    UserRoleDto toUserRoleDto(User user);

    UsernameDto toUsernameDto(User user);

}
