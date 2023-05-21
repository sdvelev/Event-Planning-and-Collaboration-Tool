package bg.sofia.uni.fmi.web.project.model;

import bg.sofia.uni.fmi.web.project.enums.CollaboratorCategory;
import bg.sofia.uni.fmi.web.project.enums.CollaboratorRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Collaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @NotNull
    private CollaboratorRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    @NotNull
    private CollaboratorCategory category;

}
