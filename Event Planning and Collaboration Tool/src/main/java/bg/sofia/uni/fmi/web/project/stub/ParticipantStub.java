package bg.sofia.uni.fmi.web.project.stub;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ParticipantStub {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
