package tn.citypulse.documentdemand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.citypulse.documentdemand.model.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

    Attachment findByDemandId(Long demandId);
}
