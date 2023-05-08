package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.PaymentMethodRequest;
import com.binar.finalproject.flightticket.dto.PaymentMethodResponse;
import com.binar.finalproject.flightticket.exception.DataAlreadyExistException;
import com.binar.finalproject.flightticket.exception.DataNotFoundException;
import com.binar.finalproject.flightticket.model.PaymentMethods;
import com.binar.finalproject.flightticket.repository.PaymentMethodRepository;
import com.binar.finalproject.flightticket.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    private String notFound = "Payment method not found";
    @Override
    public PaymentMethodResponse addPaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethods isPaymentExist = paymentMethodRepository.findByName(paymentMethodRequest.getPaymentName());
        if(isPaymentExist == null){
            PaymentMethods paymentMethods = paymentMethodRequest.toPaymentMethods();
            paymentMethodRepository.save(paymentMethods);
            return PaymentMethodResponse.build(paymentMethods);
        }
        else {
            throw new DataAlreadyExistException ("Payment method with this name already exist");
        }
    }

    @Override
    public PaymentMethodResponse searchPaymentByName(String paymentName) {
        PaymentMethods paymentMethods = paymentMethodRepository.findByName(paymentName);
        if (paymentMethods != null)
            return PaymentMethodResponse.build(paymentMethods);
        else
            throw new DataNotFoundException(notFound);
    }

    @Override
    public List<PaymentMethodResponse> gettAllPaymentMethod() {
        List<PaymentMethods> allPayment = paymentMethodRepository.findAll();
        List<PaymentMethodResponse> allPaymentResponse = new ArrayList<>();
        for (PaymentMethods paymentMethods : allPayment)
        {
            PaymentMethodResponse paymentMethodResponse = PaymentMethodResponse.build(paymentMethods);
            allPaymentResponse.add(paymentMethodResponse);
        }
        return allPaymentResponse;
    }

    @Override
    public PaymentMethodResponse updatePayment(PaymentMethodRequest paymentMethodRequest, String paymentName) {
        PaymentMethods paymentMethods = paymentMethodRepository.findByName(paymentName);
        if (paymentMethods != null)
        {
            if (paymentMethodRequest.getPaymentName() != null)
            {
                PaymentMethods paymentMethods1 = paymentMethodRepository.findByName(paymentMethodRequest.getPaymentName());
                if(paymentMethods1 == null || paymentMethods.getPaymentName().equals(paymentMethodRequest.getPaymentName()))
                    paymentMethods.setPaymentName(paymentMethodRequest.getPaymentName());
                else
                    throw new DataAlreadyExistException("Payment method with this name already exist");
            }
            if (paymentMethodRequest.getPaymentType() != null)
                paymentMethods.setPaymentType(paymentMethodRequest.getPaymentType());
            if (paymentMethodRequest.getImagePath() != null)
                paymentMethods.setImagePath(paymentMethodRequest.getImagePath());

            paymentMethodRepository.save(paymentMethods);
            return PaymentMethodResponse.build(paymentMethods);
        }
        else
            throw new DataNotFoundException("Payment method not found");
    }

    @Override
    public Boolean deletePayment(String paymentName) {
        PaymentMethods paymentMethods = paymentMethodRepository.findByName(paymentName);
        if (paymentMethods != null)
        {
            paymentMethodRepository.deleteById(paymentMethods.getPaymentId());
            return true;
        }
        else {
            throw new DataNotFoundException(notFound);
        }
    }
}
