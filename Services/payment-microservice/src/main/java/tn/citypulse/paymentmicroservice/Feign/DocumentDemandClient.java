package tn.citypulse.paymentmicroservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.citypulse.paymentmicroservice.config.FeignConfig;
import tn.citypulse.shared.dto.DocumentTypeResponseDTO;
import tn.citypulse.shared.dto.PaymentUpdateDTO;
import tn.citypulse.shared.enums.DocumentType;

@FeignClient(name = "DEMAND-SERVICE" , configuration = FeignConfig.class)
public interface DocumentDemandClient {
    @PutMapping(value = "/demands/paymentStatus", consumes = "application/json")
    public ResponseEntity<Void> updatePaymentStatus(@RequestBody PaymentUpdateDTO dto);
    @GetMapping("/demands/DemandType/{id}")
    DocumentTypeResponseDTO getDemandType(@PathVariable Long id);
}

