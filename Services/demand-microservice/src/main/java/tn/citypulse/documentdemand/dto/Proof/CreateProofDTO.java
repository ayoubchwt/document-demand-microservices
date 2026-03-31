package tn.citypulse.documentdemand.dto.Proof;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import tn.citypulse.documentdemand.model.Enum.ProofType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProofDTO {

    @NotNull(message = "Document demand id is required")
    private Long id;

    @NotNull(message = "Proof type is required")
    private String type;

    @NotNull(message = "Proof file is required")
    private MultipartFile file;
}
