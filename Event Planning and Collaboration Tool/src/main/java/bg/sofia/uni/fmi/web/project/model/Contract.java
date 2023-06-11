package bg.sofia.uni.fmi.web.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reviews")
@Table(name = "reviews")
@DynamicUpdate
@DynamicInsert
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean finished;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event associatedEvent;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor associatedVendor;

    @Column(length = 255, nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private LocalDateTime creationTime;

    @Column(length = 255)
    private String updatedBy;

    @Column
    private LocalDateTime lastUpdatedTime;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Objects.equals(totalPrice, contract.totalPrice) &&
            Objects.equals(associatedEvent, contract.associatedEvent) &&
            Objects.equals(associatedVendor, contract.associatedVendor) &&
            Objects.equals(createdBy, contract.createdBy) &&
            Objects.equals(creationTime, contract.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPrice, associatedEvent, associatedVendor, createdBy, creationTime);
    }
}