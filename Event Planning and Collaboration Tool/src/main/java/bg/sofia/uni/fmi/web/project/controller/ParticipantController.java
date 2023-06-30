package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.ParticipantDto;
import bg.sofia.uni.fmi.web.project.enums.UserRole;
import bg.sofia.uni.fmi.web.project.mapper.ParticipantMapper;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.service.ParticipantService;
import bg.sofia.uni.fmi.web.project.service.ParticipantUserEventFacadeService;
import bg.sofia.uni.fmi.web.project.service.UserParticipantFacadeService;
import bg.sofia.uni.fmi.web.project.service.UserService;
import bg.sofia.uni.fmi.web.project.service.security.TokenManagerService;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static bg.sofia.uni.fmi.web.project.config.security.RequestManager.getUserByRequest;

@RestController
@RequestMapping(path = "/participants")
@Validated
public class ParticipantController {

    private final ParticipantService participantService;
    private final UserService userService;
    private final ParticipantUserEventFacadeService participantUserEventFacadeService;
    private final ParticipantMapper participantMapper;
    private final TokenManagerService tokenManagerService;
    private final UserParticipantFacadeService userParticipantFacadeService;

    @Autowired
    public ParticipantController(ParticipantService participantService,
                                 ParticipantUserEventFacadeService participantUserEventFacadeService ,
                                 ParticipantMapper participantMapper,
                                 TokenManagerService tokenManagerService,
                                 UserService userService,
                                 UserParticipantFacadeService userParticipantFacadeService) {
        this.participantService = participantService;
        this.participantUserEventFacadeService = participantUserEventFacadeService;
        this.participantMapper = participantMapper;
        this.tokenManagerService = tokenManagerService;
        this.userService = userService;
        this.userParticipantFacadeService = userParticipantFacadeService;
    }

    @GetMapping
    public List<ParticipantDto> getAllParticipants() {

        return participantMapper.toDtoList(participantService.getParticipants());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ParticipantDto getParticipantById(@RequestParam("id")
                                                 @NotNull(message = "The provided participant id cannot be null")
                                                 @Positive(message = "The provided participant id must be positive")
                                                 Long id)  {

        Optional<Participant> potentialParticipant = participantService.getParticipantById(id);

        if (potentialParticipant.isPresent()) {
            return participantMapper.toDto(potentialParticipant.get());
        }

        throw new ResourceNotFoundException("Participant with such an id cannot be found");
    }

    @GetMapping(value = "/search", params = {"associated_user_id"})
    public List<ParticipantDto> getAllParticipantsByUserId(@RequestParam("associated_user_id")
                                             @NotNull(message = "The provided user id cannot be null")
                                             @Positive(message = "The provided user id must be positive")
                                             Long userId)  {

        List<Participant> potentialParticipants = userParticipantFacadeService.getParticipantsByUserId(userId);

        return participantMapper.toDtoList(potentialParticipants);
    }

    @PostMapping(params = {"assigned_user_id", "assigned_event_id"})
    public Long addParticipant(@NotNull(message = "The provided participant dto as body of the query cannot be null")
                               @RequestBody ParticipantDto participantDto,
                               @NotNull(message = "The provided assigned user id id cannot be null")
                               @Positive(message = "The provided assigned user id must be positive")
                               @RequestParam("assigned_user_id") Long assignedUserId,
                               @NotNull(message = "The provided assigned event id cannot be null")
                               @Positive(message = "The provided assigned event id must be positive")
                               @RequestParam("assigned_event_id") Long assignedEventId,
                               HttpServletRequest request) {

        // This method will be similar to the "invite collaborator" method. However, we will use
        // authorization to get the user id. Moreover, confirmation by email might be required

        Participant potentialParticipantToCreate = participantUserEventFacadeService
            .createParticipantWithUserAndEvent(participantMapper.toEntity(participantDto), assignedUserId,
                assignedEventId, getUserByRequest(request, tokenManagerService, userService));

        if (potentialParticipantToCreate != null) {
            return potentialParticipantToCreate.getId();
        }

        throw new ApiBadRequest("There is a problem in creating a participant");
    }

    @DeleteMapping(params = {"id"})
    public boolean removeParticipantById(@RequestParam("id")
                                         @NotNull(message = "The provided participant id cannot be null")
                                         @Positive(message = "The provided participant id must be positive")
                                         Long participantId,
                                         HttpServletRequest request) {
        return participantService.deleteParticipantByRole(participantId,
            getUserByRequest(request, tokenManagerService, userService));
    }

    @PatchMapping(value = "/role", params = {"participant_id", "user_role"})
    public boolean setUserRoleByParticipantId(@RequestParam("participant_id")
                                              @NotNull(message = "The provided participant id cannot be null")
                                              @Positive(message = "The provided participant id must be positive")
                                              Long participantId,
                                              @RequestParam("user_role")
                                              @NotNull(message = "The provided user role cannot be null")
                                              UserRole userRole,
                                              HttpServletRequest request) {
        return participantService.setUserRoleByParticipantId(participantId, userRole,
            getUserByRequest(request, tokenManagerService, userService));
    }

    @PutMapping(value = "/set", params = {"participant_id"})
    public boolean setParticipantByParticipantId(@RequestParam("participant_id")
                                   @NotNull(message = "The provided participant id cannot be null")
                                   @Positive(message = "The provided participant id must be positive")
                                   Long participantId,
                                                 @RequestBody
                                   @NotNull(message = "The provided participant dto as body of the query cannot be null")
                                   ParticipantDto participantToUpdate,
                                   HttpServletRequest request) {

        return participantService.setParticipantById(participantToUpdate, participantId,
            getUserByRequest(request, tokenManagerService, userService));
    }
}