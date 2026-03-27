package tn.citypulse.paymentmicroservice.Service.Implementation;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.citypulse.paymentmicroservice.Service.IPaymentService;
import tn.citypulse.paymentmicroservice.config.StripeConfig;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final StripeConfig stripeConfig;
    @Override
    public String createPayment(Long demandId) {
        // TO-DO : getting the demand price
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/documents/success")
                    .setCancelUrl("http://localhost:8080/documents/cancel")
                    .putMetadata("documentId",demandId.toString())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount(100L) // amount in cents
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
        Event event ;
        try {
            event = Webhook.constructEvent(payload,signHeader,stripeConfig.getWebhookSecret());
        }
        catch (Exception e) {
            throw new RuntimeException("Invalid webhook signature", e);
        }
        switch (event.getType()) {
            case "checkout.session.completed" :
                Session session = (Session) event.getDataObjectDeserializer().getObject()
                        .orElseThrow(() -> new RuntimeException("cannot deserialize session"));
                System.out.print("payment went successful");
                String demandId = session.getMetadata().get("demandId");
                // to do , mark document request as paid in db
            case "payment_intent.payment_failed" :
                // should be logged but I don't have logger
                System.out.print("payment failed");
            default:
                // should be logged but I don't have logger
                System.out.println("Unhandled event: " + event.getType());
        }
    }
}
