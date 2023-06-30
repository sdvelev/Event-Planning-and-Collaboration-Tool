package bg.sofia.uni.fmi.web.project.model;

import bg.sofia.uni.fmi.web.project.enums.VendorType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vendors")
@Table(name = "vendors")
@DynamicUpdate
@DynamicInsert
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String surname;

    @Column(length = 255, nullable = false)
    private String address;

    @Column(length = 255, nullable = false, unique = true)
    private String phoneNumber;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "vendor_type")
    @NotNull
    private VendorType vendorType;

    @OneToMany(mappedBy = "associatedVendor")
    Set<Review> vendorReviews;

    @OneToMany(mappedBy = "associatedVendor")
    Set<Contract> vendorContracts;

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
        Vendor vendor = (Vendor) o;
        return id == vendor.id && Objects.equals(name, vendor.name) &&
            Objects.equals(surname, vendor.surname) && Objects.equals(address, vendor.address) &&
            Objects.equals(phoneNumber, vendor.phoneNumber) && Objects.equals(email, vendor.email) &&
            vendorType == vendor.vendorType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, address, phoneNumber, email, vendorType);
    }
}