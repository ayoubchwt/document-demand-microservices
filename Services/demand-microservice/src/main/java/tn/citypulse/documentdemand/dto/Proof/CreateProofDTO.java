package tn.citypulse.documentdemand.dto.Proof;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.citypulse.documentdemand.model.Enum.ProofType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProofDTO {

    @NotNull(message = "Proof type is required")
    private ProofType type;

    @NotBlank(message = "Proof file URL is required")
    private String fileUrl;
}
