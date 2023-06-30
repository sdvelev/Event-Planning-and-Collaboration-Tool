package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM contracts c WHERE c.deleted = false AND c.id = ?1")
    Contract findContractByIdEquals(long id);

    @Override
    @Query("SELECT c FROM contracts c WHERE c.deleted = false")
    List<Contract> findAll();

    @Query("SELECT c FROM contracts c WHERE c.deleted = false AND c.finished = ?1")
    List<Contract> findContractsByFinishedEquals(boolean finished);

    @Query("SELECT c FROM contracts c WHERE c.deleted = false AND c.associatedEvent.id = ?1")
    List<Contract> findContractsByAssociatedEventIdEquals(long id);

    @Query("SELECT c FROM contracts c WHERE c.deleted = false AND c.associatedVendor.id = ?1")
    List<Contract> findContractsAssociatedVendorIdEquals(long id);

    @Query("SELECT c FROM contracts c WHERE c.deleted = false AND c.associatedEvent.id = ?1 AND c.associatedVendor.id = ?2")
    Contract findContractsByAssociatedEventIdEqualsAndAssociatedVendorIdEquals(long eventId, long vendorId);
}