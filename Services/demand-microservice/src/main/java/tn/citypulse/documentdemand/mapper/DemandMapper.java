package tn.citypulse.documentdemand.mapper;
import org.mapstruct    .Mapper;
import org.mapstruct.Mapping;
import tn.citypulse.documentdemand.dto.Demand.CreateDemandDTO;
import tn.citypulse.documentdemand.dto.Demand.DemandResponseDTO;
import tn.citypulse.documentdemand.dto.Demand.UpdateDemandStatusDTO;
import tn.citypulse.documentdemand.model.Demand;

@Mapper(componentModel = "spring", uses = {ProofMapper.class})
public interface DemandMapper {
    Demand toEntity(CreateDemandDTO dto);

    Demand toEntity(UpdateDemandStatusDTO dto);

    DemandResponseDTO toDTO(Demand demand);
}
