package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndDeletedFalse(Long id);
    List<User> findAllByDeletedFalse();
    List<User> findByEmailAndDeletedFalse(String email);
    List<User> findByUsernameAndDeletedFalse(String username);
}
