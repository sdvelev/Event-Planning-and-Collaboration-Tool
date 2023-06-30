package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.enums.VendorType;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("SELECT v FROM vendors v WHERE v.deleted = false AND v.id = ?1")
    Vendor findVendorByIdEquals(long id);

    @Query("SELECT v FROM vendors v WHERE v.deleted = false AND v.phoneNumber = ?1")
    Vendor findVendorByPhoneNumberEquals(String phoneNumber);

    @Query("SELECT v FROM vendors v WHERE v.deleted = false AND v.email = ?1")
    Vendor findVendorByEmailEquals(String email);

    @Override
    @Query("SELECT v FROM vendors v WHERE v.deleted = false")
    List<Vendor> findAll();

    @Query("SELECT v FROM vendors v WHERE v.deleted = false AND v.name = ?1 AND v.surname = ?2")
    List<Vendor> findVendorsByNameAndSurnameEquals(String name, String surname);

    @Query("SELECT v FROM vendors v WHERE v.deleted = false AND v.vendorType = ?1")
    List<Vendor> findVendorsByVendorTypeEquals(VendorType vendorType);
}