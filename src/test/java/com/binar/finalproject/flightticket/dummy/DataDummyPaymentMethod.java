package com.binar.finalproject.flightticket.dummy;

import com.binar.finalproject.flightticket.dto.PaymentMethodRequest;
import com.binar.finalproject.flightticket.model.PaymentMethods;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class DataDummyPaymentMethod {

    private final List<PaymentMethods> DATA_PAYMENT = new ArrayList<>();
    private final List<PaymentMethodRequest> DATA_PAYMENT_REQUEST = new ArrayList<>();

    public DataDummyPaymentMethod(){
        PaymentMethodRequest paymentMethodRequest1 = new PaymentMethodRequest();
        paymentMethodRequest1.setPaymentType("Virtual Account");
        paymentMethodRequest1.setPaymentName("BCA");
        paymentMethodRequest1.setImagePath("image-bca");

        PaymentMethodRequest paymentMethodRequest2 = new PaymentMethodRequest();
        paymentMethodRequest2.setPaymentType("Virtual Account");
        paymentMethodRequest2.setPaymentName("Mandiri");
        paymentMethodRequest2.setImagePath("image-mandiri");

        PaymentMethodRequest paymentMethodRequest3 = new PaymentMethodRequest();
        paymentMethodRequest3.setPaymentType("Virtual Account");
        paymentMethodRequest3.setPaymentName("BNI");
        paymentMethodRequest3.setImagePath("image-bni");

        PaymentMethodRequest paymentMethodRequest4 = new PaymentMethodRequest();
        paymentMethodRequest4.setPaymentType("E-Money");
        paymentMethodRequest4.setPaymentName("OVO");
        paymentMethodRequest4.setImagePath("image-ovo");

        PaymentMethodRequest paymentMethodRequest5 = new PaymentMethodRequest();
        paymentMethodRequest5.setPaymentType("E-Money");
        paymentMethodRequest5.setPaymentName("GoPay");
        paymentMethodRequest5.setImagePath("image-gopay");

        PaymentMethods paymentMethods1 = paymentMethodRequest1.toPaymentMethods();
        PaymentMethods paymentMethods2 = paymentMethodRequest2.toPaymentMethods();
        PaymentMethods paymentMethods3 = paymentMethodRequest3.toPaymentMethods();
        PaymentMethods paymentMethods4 = paymentMethodRequest4.toPaymentMethods();
        PaymentMethods paymentMethods5 = paymentMethodRequest5.toPaymentMethods();

        DATA_PAYMENT_REQUEST.add(paymentMethodRequest1);
        DATA_PAYMENT_REQUEST.add(paymentMethodRequest2);
        DATA_PAYMENT_REQUEST.add(paymentMethodRequest3);
        DATA_PAYMENT_REQUEST.add(paymentMethodRequest4);
        DATA_PAYMENT_REQUEST.add(paymentMethodRequest5);

        DATA_PAYMENT.add(paymentMethods1);
        DATA_PAYMENT.add(paymentMethods2);
        DATA_PAYMENT.add(paymentMethods3);
        DATA_PAYMENT.add(paymentMethods4);
        DATA_PAYMENT.add(paymentMethods5);
    }

    public Optional<PaymentMethods> getPaymentByName(String paymentName){
        return DATA_PAYMENT.stream()
                .filter(paymentMethods -> paymentMethods.getPaymentName().equals(paymentName))
                .findFirst();
    }
}
