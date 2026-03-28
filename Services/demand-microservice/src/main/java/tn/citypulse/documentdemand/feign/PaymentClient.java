package tn.citypulse.documentdemand.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {
    @GetMapping("/payment")
    String hello();
}
