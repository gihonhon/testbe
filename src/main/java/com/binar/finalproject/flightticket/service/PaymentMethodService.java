package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.PaymentMethodRequest;
import com.binar.finalproject.flightticket.dto.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    PaymentMethodResponse addPaymentMethod(PaymentMethodRequest paymentMethodRequest);
    PaymentMethodResponse searchPaymentByName(String paymentName);
    List<PaymentMethodResponse> gettAllPaymentMethod();
    PaymentMethodResponse updatePayment(PaymentMethodRequest paymentMethodRequest, String paymentName);
    Boolean deletePayment(String paymentName);
}
