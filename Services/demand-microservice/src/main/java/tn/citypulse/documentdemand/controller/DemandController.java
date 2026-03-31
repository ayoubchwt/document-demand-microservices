package tn.citypulse.documentdemand.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.citypulse.documentdemand.dto.Attachment.AttachmentResponseDTO;
import tn.citypulse.documentdemand.dto.Attachment.CreateAttachmentDTO;
import tn.citypulse.documentdemand.dto.Demand.CreateDemandDTO;
import tn.citypulse.documentdemand.dto.Demand.DemandResponseDTO;
import tn.citypulse.documentdemand.dto.Demand.UpdateDemandStatusDTO;
import tn.citypulse.documentdemand.dto.Proof.CreateProofDTO;
import tn.citypulse.documentdemand.dto.Proof.ProofResponseDTO;
import tn.citypulse.documentdemand.mapper.AttachmentMapper;
import tn.citypulse.documentdemand.mapper.DemandMapper;
import tn.citypulse.documentdemand.mapper.ProofMapper;
import tn.citypulse.documentdemand.model.Attachment;
import tn.citypulse.documentdemand.model.Demand;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.documentdemand.model.Enum.ProofType;
import tn.citypulse.documentdemand.model.Proof;
import tn.citypulse.documentdemand.service.IAttachmentService;
import tn.citypulse.documentdemand.service.ICloudinaryService;
import tn.citypulse.documentdemand.service.IDemandService;
import tn.citypulse.documentdemand.service.IProofService;
import tn.citypulse.shared.dto.DocumentTypeResponseDTO;
import tn.citypulse.shared.dto.PaymentUpdateDTO;
import tn.citypulse.shared.enums.DocumentType;

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

    // create demand by user
    @PostMapping
    public ResponseEntity<DemandResponseDTO> createDemand(@RequestBody CreateDemandDTO dto) {
        Demand demand = demandService.createDemand(demandMapper.toEntity(dto));
        return new ResponseEntity<>(demandMapper.toDTO(demand), HttpStatus.CREATED);
    }

    // get all demands by municipality
    @GetMapping
    public ResponseEntity<List<DemandResponseDTO>> getAllDemands() {
        List<Demand> demands = demandService.getAllDemands();
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    // get one demand by municipality
    @GetMapping("/{id}")
    public ResponseEntity<DemandResponseDTO> getDemandById(@PathVariable Long id) {
        Demand demand = demandService.getDemandById(id);
        return ResponseEntity.ok(demandMapper.toDTO(demand));
    }
    // update demand status by municipality
    @PutMapping("/demandStatus")
    public ResponseEntity<DemandResponseDTO> updateDemandStatus(@RequestBody UpdateDemandStatusDTO dto) {
        Demand updated = demandService.updateDemandStatus(demandMapper.toEntity(dto)); // needs to be entity before it goes through the function
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }

    // update demand payment status when user create a payment
    @PutMapping("/paymentStatus")
    public ResponseEntity<Void> updatePaymentStatus(@RequestBody PaymentUpdateDTO dto) {
        Demand updated = demandService.updatePaymentStatus(dto.getDemandId(),dto.getPaymentStatus());
        return ResponseEntity.ok().build();
    }
    // getting the demand type ( and the price) for the payment
    @GetMapping("/DemandType/{id}")
    public ResponseEntity<DocumentTypeResponseDTO> getDemandType(@PathVariable Long id) {
        DocumentType type = demandService.getDemandTypeById(id);
        return ResponseEntity.ok(new DocumentTypeResponseDTO(type));
    }
    // attach the requested doc by municipality after the docPaymentStatus is registered as "PAID"
    @PutMapping("/attachment")
    public ResponseEntity<DemandResponseDTO> attachDocument(@ModelAttribute CreateAttachmentDTO dto) {
        Demand updated = demandService.attachDocument(dto.getId(), dto.getFile());
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }
    // attach proof of the demand by user
    @PutMapping("/proof")
    public ResponseEntity<DemandResponseDTO> proofDocument( @ModelAttribute CreateProofDTO dto) {
        Demand updated = demandService.proofDocument(dto.getId(), dto.getFile(), dto.getType());
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }
    // get the proof by the municipality
    @GetMapping("/{id}/proof")
    public ResponseEntity<ProofResponseDTO> getDocumentProof(
            @PathVariable Long id) {
        Proof proof = proofService.getProofByDemandId(id);
        return ResponseEntity.ok(proofMapper.toDTO(proof));
    }
    // get the attachment by the user
    @GetMapping("/{id}/attachment")
    public ResponseEntity<AttachmentResponseDTO> getDocumentAttachment(
            @PathVariable Long id) {
        Attachment attachment = attachmentService.getAttachmentByDemandId(id);
        return ResponseEntity.ok(attachmentMapper.toDTO(attachment));
    }
    // delete demand by the user or municipality
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemand(@PathVariable Long id) {
        demandService.deleteDemand(id);
        return ResponseEntity.noContent().build();
    }
    // get my demands (user)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DemandResponseDTO>> getDemandsByUser(@PathVariable Long userId) {
        List<Demand> demands = demandService.getDemandsByUser(userId);
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
    // get my demands by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<DemandResponseDTO>> getDemandsByStatus(@PathVariable DemandStatus status) {
        List<Demand> demands = demandService.getDemandsByStatus(status);
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}
