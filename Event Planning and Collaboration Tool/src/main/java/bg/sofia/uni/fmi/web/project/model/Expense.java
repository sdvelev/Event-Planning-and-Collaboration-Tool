package bg.sofia.uni.fmi.web.project.model;

import bg.sofia.uni.fmi.web.project.enums.ExpenditureCategory;
import bg.sofia.uni.fmi.web.project.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "expense")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "expenditure_category")
    @NotNull
    private ExpenditureCategory expenditureCategory;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "approved", columnDefinition = "boolean default false")
    private boolean approved;

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

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event associatedEvent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return id.equals(expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
