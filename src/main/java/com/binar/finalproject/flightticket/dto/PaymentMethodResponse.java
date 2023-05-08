package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.PaymentMethods;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentMethodResponse {
    private Integer paymentId;
    private String paymentName;
    private String paymentType;
    private String imagePath;

    public static PaymentMethodResponse build(PaymentMethods paymentMethods){
        return PaymentMethodResponse.builder()
                .paymentId(paymentMethods.getPaymentId())
                .paymentName(paymentMethods.getPaymentName())
                .paymentType(paymentMethods.getPaymentType())
                .imagePath(paymentMethods.getImagePath())
                .build();
    }
}
