package tn.citypulse.documentdemand.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.citypulse.documentdemand.dto.Attachment.AttachmentResponseDTO;
import tn.citypulse.documentdemand.dto.Demand.CreateDemandDTO;
import tn.citypulse.documentdemand.dto.Demand.DemandResponseDTO;
import tn.citypulse.documentdemand.dto.Demand.UpdateDemandStatusDTO;
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
import tn.citypulse.documentdemand.service.IDemandService;
import tn.citypulse.documentdemand.service.IProofService;
import tn.citypulse.shared.dto.DocumentTypeResponseDTO;
import tn.citypulse.shared.dto.PaymentUpdateDTO;
import tn.citypulse.shared.enums.DocumentType;

import java.util.List;

@RestController
@RequestMapping("/demands")
@CrossOrigin("http://localhost:8087")
@AllArgsConstructor
@Tag(name = "Demands", description = "Endpoints for managing document demands, proofs, and attachments")
public class DemandController {
    private final IDemandService demandService;
    private final IAttachmentService attachmentService;
    private final IProofService proofService;
    private final DemandMapper demandMapper;
    private final AttachmentMapper attachmentMapper;
    private final ProofMapper proofMapper;

    // create demand by user
    @Operation(summary = "Create a new demand", description = "Allows a user to submit a new document demand.")
    @PostMapping
    public ResponseEntity<DemandResponseDTO> createDemand(@RequestBody CreateDemandDTO dto) {
        Demand demand = demandService.createDemand(demandMapper.toEntity(dto));
        return new ResponseEntity<>(demandMapper.toDTO(demand), HttpStatus.CREATED);
    }

    // get all demands by municipality
    @Operation(summary = "Get all demands", description = "Retrieves a list of all document demands for the municipality.")
    @GetMapping
    public ResponseEntity<List<DemandResponseDTO>> getAllDemands() {
        List<Demand> demands = demandService.getAllDemands();
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    // get one demand by municipality
    @Operation(summary = "Get a demand by ID", description = "Retrieves the details of a specific demand using its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DemandResponseDTO> getDemandById(
            @Parameter(description = "The ID of the demand to retrieve") @PathVariable Long id) {
        Demand demand = demandService.getDemandById(id);
        return ResponseEntity.ok(demandMapper.toDTO(demand));
    }

    // update demand status by municipality
    @Operation(summary = "Update demand status", description = "Updates the processing status of an existing demand.")
    @PutMapping("/demandStatus")
    public ResponseEntity<DemandResponseDTO> updateDemandStatus(@RequestBody UpdateDemandStatusDTO dto) {
        Demand updated = demandService.updateDemandStatus(demandMapper.toEntity(dto)); // needs to be entity before it goes through the function
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }

    // update demand payment status when user create a payment
    @Operation(summary = "Update payment status", description = "Updates the payment status of a demand after a user processes a payment.")
    @PutMapping("/paymentStatus")
    public ResponseEntity<Void> updatePaymentStatus(@RequestBody PaymentUpdateDTO dto) {
        demandService.updatePaymentStatus(dto.getDemandId(),dto.getPaymentStatus());
        return ResponseEntity.ok().build();
    }

    // getting the demand type ( and the price) for the payment
    @Operation(summary = "Get demand type and price", description = "Retrieves the document type and associated price required for payment processing.")
    @GetMapping("/DemandType/{id}")
    public ResponseEntity<DocumentTypeResponseDTO> getDemandType(
            @Parameter(description = "The ID of the demand") @PathVariable Long id) {
        DocumentType type = demandService.getDemandTypeById(id);
        return ResponseEntity.ok(new DocumentTypeResponseDTO(type));
    }

    // attach the requested doc by municipality after the docPaymentStatus is registered as "PAID"
    @Operation(summary = "Attach document (Municipality)", description = "Uploads and attaches the finalized document to the demand once payment is PAID.")
    @PutMapping(value = "/attachment", consumes = "multipart/form-data")
    public ResponseEntity<DemandResponseDTO> attachDocument(
            @Parameter(description = "The ID of the demand") @RequestParam Long id,
            @Parameter(description = "The requested document file to attach") @RequestParam MultipartFile file) {
        Demand updated = demandService.attachDocument(id, file);
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }

    // attach proof of the demand by user
    @Operation(summary = "Attach proof document (User)", description = "Uploads a proof document (e.g., ID card) required for the demand.")
    @PutMapping(value = "/proof",consumes = "multipart/form-data")
    public ResponseEntity<DemandResponseDTO> proofDocument(
            @Parameter(description = "The ID of the demand") @RequestParam Long id,
            @Parameter(description = "The proof file being uploaded") @RequestParam MultipartFile file,
            @Parameter(description = "The type of proof provided") @RequestParam ProofType type) {
        Demand updated = demandService.proofDocument(id, file, type);
        return ResponseEntity.ok(demandMapper.toDTO(updated));
    }

    // get the proof by the municipality
    @Operation(summary = "Get proof document", description = "Retrieves the proof document associated with a specific demand ID.")
    @GetMapping("/{id}/proof")
    public ResponseEntity<ProofResponseDTO> getDocumentProof(
            @Parameter(description = "The ID of the demand") @PathVariable Long id) {
        Proof proof = proofService.getProofByDemandId(id);
        return ResponseEntity.ok(proofMapper.toDTO(proof));
    }

    // get the attachment by the user
    @Operation(summary = "Get attached document", description = "Retrieves the finalized attached document for a specific demand.")
    @GetMapping("/{id}/attachment")
    public ResponseEntity<AttachmentResponseDTO> getDocumentAttachment(
            @Parameter(description = "The ID of the demand") @PathVariable Long id) {
        Attachment attachment = attachmentService.getAttachmentByDemandId(id);
        return ResponseEntity.ok(attachmentMapper.toDTO(attachment));
    }

    // delete demand by the user or municipality
    @Operation(summary = "Delete a demand", description = "Deletes a demand using its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemand(
            @Parameter(description = "The ID of the demand to delete") @PathVariable Long id) {
        demandService.deleteDemand(id);
        return ResponseEntity.noContent().build();
    }

    // get my demands (user)
    @Operation(summary = "Get demands by user", description = "Retrieves a list of all demands created by a specific user.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DemandResponseDTO>> getDemandsByUser(
            @Parameter(description = "The ID of the user") @PathVariable Long userId) {
        List<Demand> demands = demandService.getDemandsByUser(userId);
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    // get my demands by status
    @Operation(summary = "Get demands by status", description = "Retrieves a list of demands filtered by their current status.")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<DemandResponseDTO>> getDemandsByStatus(
            @Parameter(description = "The status to filter by") @PathVariable DemandStatus status) {
        List<Demand> demands = demandService.getDemandsByStatus(status);
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}