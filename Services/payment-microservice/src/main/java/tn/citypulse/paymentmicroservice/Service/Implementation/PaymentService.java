package tn.citypulse.paymentmicroservice.Service.Implementation;
import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.citypulse.paymentmicroservice.Feign.DocumentDemandClient;
import tn.citypulse.paymentmicroservice.Service.IPaymentService;
import tn.citypulse.paymentmicroservice.config.StripeConfig;
import tn.citypulse.shared.dto.PaymentUpdateDto;
import tn.citypulse.shared.enums.PaymentStatus;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final StripeConfig stripeConfig;
    private final DocumentDemandClient documentDemandClient;
    @Override
    public String createPayment(Long demandId) {
        // TO-DO : getting the demand price
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/demands/success")
                    .setCancelUrl("http://localhost:8080/demands/cancel")
                    .putMetadata("demandId",demandId.toString())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount(100L)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Document Demand #" + demandId)
                                                                    .build()
                                                    ).build()
                                    ).build()
                    )
                    .build();
            Session session = Session.create(params);
            return session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException("Stripe payment creation failed", e);
        }
    }
    @Override
    public void handleWebHook(String payload, String signHeader){
        Event event;
        try {
            event = Webhook.constructEvent(payload,signHeader,stripeConfig.getWebhookSecret());
        }
        catch (Exception e) {
            throw new RuntimeException("Invalid webhook signature", e);
        }
        switch (event.getType()) {
            case "checkout.session.completed" -> {
                try {
                    Long demandId = extractDemandId(event);
                    // mark the demand payment as paid
                    System.out.println("demand id : " + demandId);
                    PaymentUpdateDto paymentUpdateDto = new PaymentUpdateDto(demandId,PaymentStatus.PAID);
                    documentDemandClient.updatePaymentStatus(paymentUpdateDto);
                } catch (EventDataObjectDeserializationException e) {
                    throw new RuntimeException("Could not deserialize session : " + e);
                }
            }
            case "payment_intent.payment_failed" -> {
                try {
                    Long demandId = extractDemandId(event);
                    System.out.println("demand id : " + demandId);
                    // mark the demand payment as failed
                    PaymentUpdateDto paymentUpdateDto = new PaymentUpdateDto(demandId,PaymentStatus.FAILED);
                    documentDemandClient.updatePaymentStatus(paymentUpdateDto);
                } catch (EventDataObjectDeserializationException e) {
                    throw new RuntimeException("Could not deserialize session : " + e);
                }
            }
            default -> {
                // should be logged but I don't have logger
                System.out.println("Unhandled event: " + event.getType());
            }
        }
    }
    private Long extractDemandId(Event event) throws EventDataObjectDeserializationException {
        Session session = (Session) event.getDataObjectDeserializer().deserializeUnsafe();
        return Long.parseLong(session.getMetadata().get("demandId"));
    }
}
