package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ParticipantDto;
import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto (User model);

    User toEntity(UserDto dto);

//    public UserDto toDto(User entity) {
//
//        if (entity == null) {
//            return null;
//        }
//
//        return UserDto.builder()
//            .id(entity.getId())
//            .username(entity.getUsername())
//            .password(entity.getPassword())
//            .name(entity.getName())
//            .surname(entity.getSurname())
//            .email(entity.getEmail())
//            .profilePhotoLink(entity.getProfilePhotoLink())
//            .verified(entity.isVerified())
//            .address(entity.getAddress())
//            .build();
//    }
//
//    public User toEntity(UserDto userDto) {
//
//        if (userDto == null) {
//            return null;
//        }
//
//        return User.builder()
//            .id(userDto.getId())
//            .username(userDto.getUsername())
//            .password(userDto.getPassword())
//            .name(userDto.getName())
//            .surname(userDto.getSurname())
//            .email(userDto.getEmail())
//            .profilePhotoLink(userDto.getProfilePhotoLink())
//            .verified(userDto.isVerified())
//            .address(userDto.getAddress())
//            .build();
//    }
}
