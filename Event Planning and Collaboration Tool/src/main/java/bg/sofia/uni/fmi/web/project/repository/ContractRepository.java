package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Contract findContractByIdEquals(long id);

    List<Contract> findAll();

    List<Contract> findContractsByTotalPriceEquals(BigDecimal totalPrice);

    List<Contract> findContractsByFinishedEquals(boolean finished);

    List<Contract> findContractsByAssociatedEventIdEquals(long id);

    List<Contract> findContractsByAssociatedVendorIdEquals(long id);
}