package bg.sofia.uni.fmi.web.project.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user_planner")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

//    @Column(name = "description")
//    private String description;

    @Column(name = "verified")
    @NotNull
    private boolean verified;

    @Column(name = "profile_photo_link")
    private String profilePhotoLink;

    @Column(name = "address")
    private String address;

    //Audit fields

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "last_updated_time")
    private LocalDateTime lastUpdatedTime;

    //Soft Deletion

    @Column(name = "deleted")
    @NotNull
    private boolean deleted;

    @OneToMany(mappedBy = "associatedUser")
    Set<Participant> participantProfiles;
}
