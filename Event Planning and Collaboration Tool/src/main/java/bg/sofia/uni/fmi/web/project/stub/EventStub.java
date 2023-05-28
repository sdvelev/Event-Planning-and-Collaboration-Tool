package bg.sofia.uni.fmi.web.project.stub;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EventStub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

////    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
////    private Set<Contract> contracts;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
//    private Set<Task> tasks;
//
//    @Column(length = 255, nullable = false)
//    private String name;
//
//    @Column
//    private LocalDateTime eventTime;
//
//    @Column(length = 255, nullable = false)
//    private String location;
//
//    @Column(length = 255, nullable = false)
//    private String description;
//
//    @Column
//    private BigDecimal eventBudget;
//
//    @Column
//    private BigDecimal maxFamilyBudget;
//
//    @Column
//    private BigDecimal maxFriendsBudget;
//
//    @Column
//    private BigDecimal maxColleaguesBudget;
//
////    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
////    @JoinColumn(name = "collaborator_id")
////    private Collaborator creator;
}