package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.PaymentMethods;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PaymentMethodRequest {
    @NotEmpty(message = "Payment name is required.")
    private String paymentName;
    @NotEmpty(message = "Payment type is required.")
    private String paymentType;
    @NotEmpty(message = "Image path is required.")
    private String imagePath;

    public PaymentMethods toPaymentMethods()
    {
        return PaymentMethods.builder()
                .paymentName(this.paymentName)
                .paymentType(this.paymentType)
                .imagePath(this.imagePath)
                .build();
    }
}
