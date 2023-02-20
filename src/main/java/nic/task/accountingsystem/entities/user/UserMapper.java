package nic.task.accountingsystem.entities.user;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper
public interface UserMapper {
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mapping(target = "role", source = "entity.role")
    UserDTO userToDTO(User entity);

    @Mapping(target = "role", source = "dto.role")
    User DTOtoUser(UserDTO dto);

    List<UserDTO> toListOfDTO(List<User> entities);

    @AfterMapping
    default void encodePassword(@MappingTarget User user) {
        if (user.getPassword() != null) {
            String plainText = user.getPassword();
            user.setPassword(encoder.encode(plainText));
        }
    }
}
