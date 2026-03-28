package tn.citypulse.documentdemand.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.citypulse.documentdemand.model.Proof;

public interface ProofRepository extends JpaRepository<Proof,Long> {
    Proof findByDemandId(Long demandId);
}
