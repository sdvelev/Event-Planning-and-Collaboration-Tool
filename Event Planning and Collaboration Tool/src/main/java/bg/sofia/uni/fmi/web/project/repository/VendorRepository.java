package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.enums.VendorType;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findVendorByIdEquals(long id);

    Vendor findVendorByPhoneNumberEquals(String phoneNumber);

    Vendor findVendorByEmailEquals(String email);

    List<Vendor> findAll();

    List<Vendor> findVendorsByNameAndSurnameEquals(String name, String surname);

    List<Vendor> findVendorsByVendorTypeEquals(VendorType vendorType);
}