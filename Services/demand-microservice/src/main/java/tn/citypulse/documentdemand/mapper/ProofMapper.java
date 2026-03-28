package tn.citypulse.documentdemand.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.citypulse.documentdemand.dto.Proof.CreateProofDTO;
import tn.citypulse.documentdemand.model.Proof;

@Mapper(componentModel = "spring")
public interface ProofMapper {
    Proof toEntity(CreateProofDTO dto);
    CreateProofDTO toDTO(Proof proof);
}
