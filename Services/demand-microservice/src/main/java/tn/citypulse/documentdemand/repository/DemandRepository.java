package tn.citypulse.documentdemand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.citypulse.documentdemand.model.Demand;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;

import java.util.List;

public interface DemandRepository extends JpaRepository<Demand,Long> {
    List<Demand> findAllByUserId(Long userId);

    List<Demand> findAllByStatus(DemandStatus status);
}
