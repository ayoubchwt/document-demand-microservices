package tn.citypulse.documentdemand.dto.Demand;

import jakarta.persistence.Column;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import tn.citypulse.documentdemand.model.Enum.DocumentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDemandDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Municipality ID is required")
    private Long municipalityId;

    @NotNull(message = "Document type is required")
    private DocumentType documentType;

    private String description;
}
