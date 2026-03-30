package tn.citypulse.documentdemand.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.citypulse.documentdemand.dto.Proof.CreateProofDTO;
import tn.citypulse.documentdemand.dto.Proof.ProofResponseDTO;
import tn.citypulse.documentdemand.model.Proof;

@Mapper(componentModel = "spring")
public interface ProofMapper {
    @Mapping(target = "type", ignore = true) // we set it manually
    @Mapping(target = "fileUrl", ignore = true) // we set after upload
    Proof toEntity(CreateProofDTO dto);
    @Mapping(target = "type", expression = "java(proof.getType().name())")
    ProofResponseDTO toDTO(Proof proof);
}
