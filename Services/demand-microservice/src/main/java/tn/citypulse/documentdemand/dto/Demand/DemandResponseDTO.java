package tn.citypulse.documentdemand.dto.Demand;

import lombok.AllArgsConstructor;
import lombok.Data;
import tn.citypulse.documentdemand.dto.Proof.ProofResponseDTO;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.shared.enums.DocumentType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DemandResponseDTO {
    private Long id;

    private Long userId;

    private DocumentType documentType;

    private String description;

    private DemandStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private ProofResponseDTO proof;
}
