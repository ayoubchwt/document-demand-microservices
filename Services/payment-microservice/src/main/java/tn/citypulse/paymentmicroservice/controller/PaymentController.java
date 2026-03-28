package tn.citypulse.paymentmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.citypulse.paymentmicroservice.Service.IPaymentService;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;
    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam Long demandId) {
        String paymentUrl = paymentService.createPayment(demandId);
        return ResponseEntity.ok(paymentUrl);
    }
    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebHook(@RequestBody String payload , @RequestHeader("Stripe-Signature") String sigHeader) {
        paymentService.handleWebHook(payload,sigHeader);
        return ResponseEntity.ok().build();
    }
}

