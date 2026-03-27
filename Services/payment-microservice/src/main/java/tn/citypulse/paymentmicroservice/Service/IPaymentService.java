package tn.citypulse.paymentmicroservice.Service;

public interface IPaymentService {
    String createPayment(Long demandId);
    void handleWebHook(String payload, String signHeader);
}
