package tn.citypulse.paymentmicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.codec.Encoder;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final ObjectMapper objectMapper;
    @Bean
    public Encoder feignEncoder() {
        return new JacksonEncoder(objectMapper);
    }
}
