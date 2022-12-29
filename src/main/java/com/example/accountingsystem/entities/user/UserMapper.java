package com.example.accountingsystem.entities.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {
    @Mapping(target = "role", source = "entity.role")
    UserDTO userToDTO(User entity);

    @Mapping(target = "role", source = "dto.role")
    User DTOtoUser(UserDTO dto);

    List<UserDTO> toListOfDTO(List<User> entities);
}
