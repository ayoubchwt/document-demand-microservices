package tn.citypulse.paymentmicroservice.config;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import  com.stripe.Stripe;
@Configuration
public class StripeConfig {

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
    public String getWebhookSecret() {
        return this.webhookSecret;
    }
}
