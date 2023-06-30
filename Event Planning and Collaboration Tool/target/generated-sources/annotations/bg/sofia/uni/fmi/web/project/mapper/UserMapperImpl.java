package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-30T18:19:39+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User model) {
        if ( model == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( model.getId() );
        userDto.username( model.getUsername() );
        userDto.password( model.getPassword() );
        userDto.name( model.getName() );
        userDto.surname( model.getSurname() );
        userDto.email( model.getEmail() );
        userDto.verified( model.isVerified() );
        userDto.profilePhotoLink( model.getProfilePhotoLink() );
        userDto.address( model.getAddress() );
        userDto.createdBy( model.getCreatedBy() );
        userDto.creationTime( model.getCreationTime() );
        userDto.updatedBy( model.getUpdatedBy() );
        userDto.lastUpdatedTime( model.getLastUpdatedTime() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.getId() );
        user.username( dto.getUsername() );
        user.password( dto.getPassword() );
        user.name( dto.getName() );
        user.surname( dto.getSurname() );
        user.email( dto.getEmail() );
        user.verified( dto.isVerified() );
        user.profilePhotoLink( dto.getProfilePhotoLink() );
        user.address( dto.getAddress() );
        user.createdBy( dto.getCreatedBy() );
        user.creationTime( dto.getCreationTime() );
        user.updatedBy( dto.getUpdatedBy() );
        user.lastUpdatedTime( dto.getLastUpdatedTime() );

        return user.build();
    }

    @Override
    public List<UserDto> toDtoList(List<User> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( modelList.size() );
        for ( User user : modelList ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
