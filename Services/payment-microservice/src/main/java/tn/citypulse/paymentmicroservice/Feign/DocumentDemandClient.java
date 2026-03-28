package tn.citypulse.paymentmicroservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.citypulse.shared.enums.PaymentStatus;

@FeignClient(name = "DEMAND-SERVICE")
public interface DocumentDemandClient {
    @PutMapping("/PaymentStatus/{id}")
    public ResponseEntity<Void> updatePaymentStatus(@PathVariable Long id, @RequestBody PaymentStatus status);
}

