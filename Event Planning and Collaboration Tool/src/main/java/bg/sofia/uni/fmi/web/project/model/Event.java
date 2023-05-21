package bg.sofia.uni.fmi.web.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
    private Set<Contract> contracts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
    private Set<Task> tasks;

    @Column(length = 255, nullable = false)
    private String name;

    @Column
    private LocalDateTime eventTime;

    @Column(length = 255, nullable = false)
    private String location;

    @Column(length = 255, nullable = false)
    private String description;

    @Column
    private BigDecimal eventBudget;

    @Column
    private BigDecimal maxFamilyBudget;

    @Column
    private BigDecimal maxFriendsBudget;

    @Column
    private BigDecimal maxColleaguesBudget;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "collaborator_id")
//    private Collaborator creator;
}