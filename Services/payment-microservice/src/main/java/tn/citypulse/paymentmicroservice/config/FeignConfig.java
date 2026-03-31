package tn.citypulse.paymentmicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
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
    @Bean
    public Decoder feignDecoder () {
        return new JacksonDecoder(objectMapper);
    }
}
