package tn.citypulse.paymentmicroservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.citypulse.paymentmicroservice.config.FeignConfig;
import tn.citypulse.shared.dto.PaymentUpdateDto;

@FeignClient(name = "DEMAND-SERVICE" , configuration = FeignConfig.class)
public interface DocumentDemandClient {
    @PutMapping(value = "/demands/paymentStatus", consumes = "application/json")
    public ResponseEntity<Void> updatePaymentStatus(@RequestBody PaymentUpdateDto dto);
}

