package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
