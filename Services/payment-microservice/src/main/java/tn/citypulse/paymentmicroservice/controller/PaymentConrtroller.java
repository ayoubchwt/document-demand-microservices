package tn.citypulse.paymentmicroservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentConrtroller {
    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello from payment microservice");
    }
}

