package tn.citypulse.documentdemand.dto.Demand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDemandStatusDTO {
    @NotNull(message = "Demand id is required")
    private Long id;

    @NotNull(message = "Municipality id is required")
    private Long municipalityId;

    @NotNull(message = "Status specification is required")
    private DemandStatus status;
}
