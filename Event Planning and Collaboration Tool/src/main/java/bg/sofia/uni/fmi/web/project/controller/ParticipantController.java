package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.ParticipantDto;
import bg.sofia.uni.fmi.web.project.enums.UserRole;
import bg.sofia.uni.fmi.web.project.mapper.ParticipantMapper;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.service.ParticipantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/participants")
@Validated
public class ParticipantController {

    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;

    @Autowired
    public ParticipantController(ParticipantService participantService, ParticipantMapper participantMapper) {
        this.participantService = participantService;
        this.participantMapper = participantMapper;
    }

    @GetMapping
    public List<ParticipantDto> getAllParticipants() {

        final List<Participant> toReturnList = participantService.getParticipants();

        return toReturnList.stream()
            .map(participantMapper::toDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public ParticipantDto getParticipantById(@RequestParam("id") Long id)  {

        Optional<Participant> potentialParticipant = participantService.getParticipantById(id);

        if (potentialParticipant.isPresent()) {
            return participantMapper.toDto(potentialParticipant.get());
        }

        return null;
    }

    @PostMapping
    public Long addParticipant(@Valid @NotNull @RequestBody ParticipantDto participantDto,
                               @Valid @NotNull @RequestParam("assigned_user_id") Long assignedUserId,
                               @Valid @NotNull @RequestParam("assigned_event_id") Long assignedEventId) {

        //TODO: This method will be similar to the "invite collaborator" method. However, we will use
        //      authorization to get the user id. Moreover, confirmation by email might be required

        Participant potentialParticipantToCreate = participantService
            .createParticipant(participantMapper.toEntity(participantDto), assignedUserId, assignedEventId);

        if (potentialParticipantToCreate != null) {

            return potentialParticipantToCreate.getId();
        }

        return -1L;
    }

    @DeleteMapping
    public boolean removeParticipantById(@RequestParam("id") Long participantId) {
        return participantService.deleteParticipant(participantId);
    }

    @PatchMapping("/role")
    public boolean setUserRoleByParticipantId(@RequestParam("participant_id") Long participantId,
                                              @RequestParam("user_role") UserRole userRole) {
        //TODO: This method is similar to the desired functionality of managing user roles. However, user
        //      authorization will be used so that we can check if user has the right to change someone's role
        return participantService.setUserRoleByParticipantId(participantId, userRole);
    }
}
