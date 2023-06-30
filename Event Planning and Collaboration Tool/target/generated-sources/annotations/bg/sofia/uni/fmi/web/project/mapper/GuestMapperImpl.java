package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.GuestDto;
import bg.sofia.uni.fmi.web.project.model.Guest;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-30T07:10:39+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
@Component
public class GuestMapperImpl implements GuestMapper {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public GuestDto toDto(Guest guestEntity) {
        if ( guestEntity == null ) {
            return null;
        }

        GuestDto.GuestDtoBuilder guestDto = GuestDto.builder();

        guestDto.associatedEventDto( eventMapper.toDto( guestEntity.getAssociatedEvent() ) );
        guestDto.id( guestEntity.getId() );
        guestDto.name( guestEntity.getName() );
        guestDto.surname( guestEntity.getSurname() );
        guestDto.email( guestEntity.getEmail() );
        guestDto.guestType( guestEntity.getGuestType() );
        guestDto.attendanceType( guestEntity.getAttendanceType() );
        guestDto.invitationSent( guestEntity.isInvitationSent() );
        guestDto.createdBy( guestEntity.getCreatedBy() );
        guestDto.creationTime( guestEntity.getCreationTime() );
        guestDto.updatedBy( guestEntity.getUpdatedBy() );
        guestDto.lastUpdatedTime( guestEntity.getLastUpdatedTime() );
        guestDto.deleted( guestEntity.isDeleted() );

        return guestDto.build();
    }

    @Override
    public Guest toEntity(GuestDto guestDto) {
        if ( guestDto == null ) {
            return null;
        }

        Guest.GuestBuilder guest = Guest.builder();

        guest.associatedEvent( eventMapper.toEntity( guestDto.getAssociatedEventDto() ) );
        guest.id( guestDto.getId() );
        guest.name( guestDto.getName() );
        guest.surname( guestDto.getSurname() );
        guest.email( guestDto.getEmail() );
        guest.guestType( guestDto.getGuestType() );
        guest.attendanceType( guestDto.getAttendanceType() );
        guest.invitationSent( guestDto.isInvitationSent() );
        guest.createdBy( guestDto.getCreatedBy() );
        guest.creationTime( guestDto.getCreationTime() );
        guest.updatedBy( guestDto.getUpdatedBy() );
        guest.lastUpdatedTime( guestDto.getLastUpdatedTime() );
        guest.deleted( guestDto.isDeleted() );

        return guest.build();
    }
}
