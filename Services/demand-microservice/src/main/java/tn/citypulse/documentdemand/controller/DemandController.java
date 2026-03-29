package tn.citypulse.documentdemand.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.citypulse.documentdemand.dto.Attachment.CreateAttachmentDTO;
import tn.citypulse.documentdemand.dto.Demand.CreateDemandDTO;
import tn.citypulse.documentdemand.dto.Demand.DemandResponseDTO;
import tn.citypulse.documentdemand.dto.Demand.UpdateDemandDTO;
import tn.citypulse.documentdemand.feign.PaymentClient;
import tn.citypulse.documentdemand.mapper.AttachmentMapper;
import tn.citypulse.documentdemand.mapper.DemandMapper;
import tn.citypulse.documentdemand.model.Demand;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.documentdemand.service.IAttachmentService;
import tn.citypulse.documentdemand.service.IDemandService;
import tn.citypulse.shared.dto.PaymentUpdateDto;
import tn.citypulse.shared.enums.PaymentStatus;

import java.util.List;

@RestController
@RequestMapping("/demands")
@AllArgsConstructor
public class DemandController {
    private final IDemandService demandService;
    private final IAttachmentService attachmentService;
    private final DemandMapper demandMapper;
    private final AttachmentMapper attachmentMapper;
    private final PaymentClient paymentClient;

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
    // to do : changing DemandStatus to String and using enum.name()
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
    /*
    @GetMapping("/municipality/{municipalityId}")
    public ResponseEntity<List<DemandResponseDTO>> getDemandsByMunicipality(@PathVariable Long municipalityId) {
        List<Demand> demands = demandService.getDemandsByMunicipality(municipalityId);
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
    */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<DemandResponseDTO>> getDemandsByStatus(@PathVariable DemandStatus status) {
        List<Demand> demands = demandService.getDemandsByStatus(status);
        List<DemandResponseDTO> response = demands.stream()
                .map(demandMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/payment")
    public ResponseEntity<String> payment(){
        return ResponseEntity.ok(paymentClient.hello());
    }
}
