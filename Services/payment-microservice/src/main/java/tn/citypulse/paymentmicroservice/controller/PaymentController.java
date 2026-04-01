package tn.citypulse.paymentmicroservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.citypulse.paymentmicroservice.Service.IPaymentService;

@RestController
@RequestMapping("/payment")
@CrossOrigin("http://localhost:8087")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Endpoints for managing Stripe payment sessions and secure webhooks")
public class PaymentController {

    private final IPaymentService paymentService;

    @Operation(summary = "Create a payment session", description = "Generates a Stripe checkout URL for processing the payment of a specific document demand.")
    @PostMapping("/create")
    public ResponseEntity<String> createPayment(
            @Parameter(description = "The ID of the document demand to be paid for") @RequestParam Long demandId) {
        String paymentUrl = paymentService.createPayment(demandId);
        return ResponseEntity.ok(paymentUrl);
    }

    @Hidden
    @Operation(summary = "Stripe Webhook Handler", description = "Receives and processes asynchronous webhook events from Stripe to update payment statuses securely.")
    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebHook(
            @Parameter(description = "The raw payload body sent by Stripe") @RequestBody String payload,
            @Parameter(description = "The Stripe signature header used to verify the event came securely from Stripe") @RequestHeader("Stripe-Signature") String sigHeader) {
        paymentService.handleWebHook(payload, sigHeader);
        return ResponseEntity.ok().build();
    }
}