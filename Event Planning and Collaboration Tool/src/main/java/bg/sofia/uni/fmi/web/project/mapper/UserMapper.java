package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User entity) {

        return UserDto.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .password(entity.getPassword())
            .name(entity.getName())
            .surname(entity.getSurname())
            .email(entity.getEmail())
            .profilePhotoLink(entity.getProfilePhotoLink())
            .verified(entity.isVerified())
            .address(entity.getAddress())
            .build();
    }

    public User toEntity(UserDto userDto) {

        return User.builder()
            .id(userDto.getId())
            .username(userDto.getUsername())
            .password(userDto.getPassword())
            .name(userDto.getName())
            .surname(userDto.getSurname())
            .email(userDto.getEmail())
            .profilePhotoLink(userDto.getProfilePhotoLink())
            .verified(userDto.isVerified())
            .address(userDto.getAddress())
            .build();
    }

}
