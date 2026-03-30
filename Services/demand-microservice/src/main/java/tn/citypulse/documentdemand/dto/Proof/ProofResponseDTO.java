package tn.citypulse.documentdemand.dto.Proof;

import lombok.AllArgsConstructor;
import lombok.Data;
import tn.citypulse.documentdemand.model.Enum.ProofType;

@Data
@AllArgsConstructor
public class ProofResponseDTO {

    private Long id;

    private String fileUrl;

    private String type;
}