package tn.citypulse.paymentmicroservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.citypulse.shared.dto.PaymentUpdateDto;

@FeignClient(name = "DEMAND-SERVICE")
public interface DocumentDemandClient {
    @PutMapping("/demands/paymentStatus")
    public ResponseEntity<Void> updatePaymentStatus(@RequestBody PaymentUpdateDto dto);
}

