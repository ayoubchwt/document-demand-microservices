package tn.citypulse.documentdemand.dto.Demand;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import tn.citypulse.documentdemand.dto.Proof.CreateProofDTO;
import tn.citypulse.shared.enums.DocumentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDemandDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Document type is required")
    private DocumentType documentType;

    @NotNull(message = "Document demand description is required")
    private String description;
}
