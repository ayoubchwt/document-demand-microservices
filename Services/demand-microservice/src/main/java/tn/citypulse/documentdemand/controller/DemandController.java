package tn.citypulse.documentdemand.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.citypulse.documentdemand.dto.Attachment.CreateAttachmentDTO;
import tn.citypulse.documentdemand.dto.Demand.CreateDemandDTO;
import tn.citypulse.documentdemand.dto.Demand.DemandResponseDTO;
import tn.citypulse.documentdemand.dto.Proof.CreateProofDTO;
import tn.citypulse.documentdemand.dto.Proof.ProofResponseDTO;
import tn.citypulse.documentdemand.feign.PaymentClient;
import tn.citypulse.documentdemand.mapper.AttachmentMapper;
import tn.citypulse.documentdemand.mapper.DemandMapper;
import tn.citypulse.documentdemand.mapper.ProofMapper;
import tn.citypulse.documentdemand.model.Demand;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.documentdemand.model.Enum.ProofType;
import tn.citypulse.documentdemand.model.Proof;
import tn.citypulse.documentdemand.service.IAttachmentService;
import tn.citypulse.documentdemand.service.ICloudinaryService;
import tn.citypulse.documentdemand.service.IDemandService;
import tn.citypulse.documentdemand.service.IProofService;
import tn.citypulse.shared.dto.PaymentUpdateDto;

import java.util.List;

@RestController
    @RequestMapping("/demands")
@AllArgsConstructor
public class DemandController {
    private final IDemandService demandService;
    private final IAttachmentService attachmentService;
    private final IProofService proofService;
    private final ICloudinaryService cloudinaryService;
    private final DemandMapper demandMapper;
    private final AttachmentMapper attachmentMapper;
    private final ProofMapper proofMapper;

    @PostMapping
    public ResponseEntity<DemandResponseDTO> createDemand(@RequestBody CreateDemandDTO dto) {
        Demand demand = demandService.createDemand(demandMapper.toEntity(dto));
        return new ResponseEntity<>(demandMapper.toDTO(demand), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DemandResponseDTO>> getAllDemands() {
        List<Demand> demands = demandService.getAllDemands();
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandResponseDTO> getDemandById(@PathVariable Long id) {
        Demand demand = demandService.getDemandById(id);
        return ResponseEntity.ok(demandMapper.toDTO(demand));
    }

    @PutMapping("/pemandStatus/{id}")
    public ResponseEntity<DemandResponseDTO> updateDemandStatus(@PathVariable Long id, @RequestBody DemandStatus status) {
        Demand updated = demandService.updateDemandStatus(id, status);
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }
    @PutMapping("/paymentStatus")
    public ResponseEntity<Void> updatePaymentStatus(@RequestBody PaymentUpdateDto dto) {
        Demand updated = demandService.updatePaymentStatus(dto.getDemandId(),dto.getPaymentStatus());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/attachment")
    public ResponseEntity<DemandResponseDTO> attachDocument(
            @PathVariable Long id,
            @RequestBody CreateAttachmentDTO dto) {
        Demand updated = demandService.attachDocument(id, attachmentMapper.toEntity(dto));
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }
    @PutMapping("/{id}/proof")
    public ResponseEntity<DemandResponseDTO> proofDocument(
            @PathVariable Long id,
            @ModelAttribute CreateProofDTO dto) {
        ProofType proofType;
        try {
            proofType = ProofType.valueOf(dto.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid proof type: " + dto.getType());
        }
        String uploadUrl = cloudinaryService.uploadFile(dto.getFile(), "Proof");
        Proof proof = proofMapper.toEntity(dto);
        proof.setType(proofType);
        proof.setFileUrl(uploadUrl);
        Demand updated = demandService.proofDocument(id, proof);
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }
    @GetMapping("/{id}/proof")
    public ResponseEntity<ProofResponseDTO> getDocumentProof(
            @PathVariable Long id) {
        Proof proof = proofService.getProofByDemandId(id);
        return ResponseEntity.ok(proofMapper.toDTO(proof));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemand(@PathVariable Long id) {
        demandService.deleteDemand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DemandResponseDTO>> getDemandsByUser(@PathVariable Long userId) {
        List<Demand> demands = demandService.getDemandsByUser(userId);
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<DemandResponseDTO>> getDemandsByStatus(@PathVariable DemandStatus status) {
        List<Demand> demands = demandService.getDemandsByStatus(status);
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}
