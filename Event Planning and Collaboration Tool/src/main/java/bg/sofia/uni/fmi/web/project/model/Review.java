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
import jakarta.validation.constraints.Digits;
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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal rating;

    @Column(length = 255, nullable = false)
    private String comment;

    @Column(length = 255, name = "photo_link", nullable = false)
    private String photoLink;

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
        Review review = (Review) o;
        return Objects.equals(rating, review.rating) &&
            Objects.equals(comment, review.comment) &&
            Objects.equals(photoLink, review.photoLink) &&
            Objects.equals(associatedVendor, review.associatedVendor) &&
            Objects.equals(createdBy, review.createdBy) &&
            Objects.equals(creationTime, review.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, comment, photoLink, associatedVendor, createdBy, creationTime);
    }
}