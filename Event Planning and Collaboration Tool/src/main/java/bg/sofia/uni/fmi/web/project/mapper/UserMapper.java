package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User entity) {

        return UserDto.builder()
            .username(entity.getUsername())
            .password(entity.getPassword())
            .email(entity.getEmail())
            .name(entity.getName())
            .description(entity.getDescription())
            .age(entity.getAge())
            .build();
    }

    public User toEntity(UserDto userDto) {

        return User.builder()
            .username(userDto.getUsername())
            .password(userDto.getPassword())
            .email(userDto.getEmail())
            .name(userDto.getName())
            .description(userDto.getDescription())
            .age(userDto.getAge())
            .build();
    }

}
