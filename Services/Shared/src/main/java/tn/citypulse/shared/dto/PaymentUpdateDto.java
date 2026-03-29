package tn.citypulse.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.citypulse.shared.enums.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentUpdateDto {
    private Long demandId;
    private PaymentStatus paymentStatus;
}
