package com.binar.finalproject.flightticket.services;

import com.binar.finalproject.flightticket.dto.PaymentMethodRequest;
import com.binar.finalproject.flightticket.dummy.DataDummyPaymentMethod;
import com.binar.finalproject.flightticket.exception.DataAlreadyExistException;
import com.binar.finalproject.flightticket.exception.DataNotFoundException;
import com.binar.finalproject.flightticket.model.PaymentMethods;
import com.binar.finalproject.flightticket.repository.PaymentMethodRepository;
import com.binar.finalproject.flightticket.service.impl.PaymentMethodServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PaymentMethodServiceImplTest {

    @Mock
    PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    private DataDummyPaymentMethod dataDummyPaymentMethod;
    private List<PaymentMethodRequest> dataPaymentMethodRequest = new ArrayList<>();
    private List<PaymentMethods> dataPaymentMethods = new ArrayList<>();

    @BeforeEach
    void Init(){
        MockitoAnnotations.initMocks(this);
        dataDummyPaymentMethod = new DataDummyPaymentMethod();
        dataPaymentMethodRequest = dataDummyPaymentMethod.getDATA_PAYMENT_REQUEST();
        dataPaymentMethods = dataDummyPaymentMethod.getDATA_PAYMENT();
    }

    @Test
    @DisplayName("[Positive] Register new payment method")
    void testPositiveRegisterPaymentMethod(){
        Mockito.when(paymentMethodRepository.findByName(dataPaymentMethodRequest.get(0).getPaymentName())).thenReturn(null);
        Mockito.when(paymentMethodRepository.save(dataPaymentMethods.get(0))).thenReturn(dataPaymentMethods.get(0));
        var actualValue = paymentMethodService.addPaymentMethod(dataPaymentMethodRequest.get(0));
        var expectedValue1 = dataPaymentMethodRequest.get(0).getPaymentType();
        var expectedValue2 = dataPaymentMethodRequest.get(0).getPaymentName();
        var expectedValue3 = dataPaymentMethodRequest.get(0).getImagePath();
        Assertions.assertNotNull(actualValue);
        Assertions.assertNotNull(expectedValue1);
        Assertions.assertNotNull(expectedValue2);
        Assertions.assertNotNull(expectedValue3);
        Assertions.assertEquals(expectedValue1, actualValue.getPaymentType());
        Assertions.assertEquals(expectedValue2, actualValue.getPaymentName());
        Assertions.assertEquals(expectedValue3, actualValue.getImagePath());
    }

    @Test
    @DisplayName("[Negative] Register new payment method")
    void testNegativeRegisterPaymentMethod(){
        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            Mockito.when(paymentMethodRepository.findByName(dataPaymentMethodRequest.get(0).getPaymentName())).thenReturn(dataPaymentMethods.get(0));
            paymentMethodService.addPaymentMethod(dataPaymentMethodRequest.get(0));
        });

        var expectedValue = "Payment method with this name already exist";
        Assertions.assertEquals(expectedValue, exception.getMessage());
    }

    @Test
    @DisplayName("[Positive] Get All payment method")
    void testPositiveGetAllPaymentMethod(){
        Mockito.when(paymentMethodRepository.findAll()).thenReturn(dataPaymentMethods);
        var actualValue = paymentMethodService.gettAllPaymentMethod();
        var expectedSize = dataPaymentMethods.size();
        var expectedValue1 = dataPaymentMethods.get(0);
        var expectedValue2 = dataPaymentMethods.get(1);
        var expectedValue3 = dataPaymentMethods.get(2);
        var expectedValue4 = dataPaymentMethods.get(3);
        var expectedValue5 = dataPaymentMethods.get(4);

        Assertions.assertNotNull(actualValue);
        Assertions.assertNotNull(expectedValue1);
        Assertions.assertNotNull(expectedValue2);
        Assertions.assertNotNull(expectedValue3);
        Assertions.assertNotNull(expectedValue4);
        Assertions.assertNotNull(expectedValue5);
        Assertions.assertEquals(expectedSize, actualValue.size());

        Assertions.assertEquals(expectedValue1.getPaymentType(), actualValue.get(0).getPaymentType());
        Assertions.assertEquals(expectedValue1.getPaymentName(), actualValue.get(0).getPaymentName());
        Assertions.assertEquals(expectedValue1.getImagePath(), actualValue.get(0).getImagePath());

        Assertions.assertEquals(expectedValue2.getPaymentType(), actualValue.get(1).getPaymentType());
        Assertions.assertEquals(expectedValue2.getPaymentName(), actualValue.get(1).getPaymentName());
        Assertions.assertEquals(expectedValue2.getImagePath(), actualValue.get(1).getImagePath());

        Assertions.assertEquals(expectedValue3.getPaymentType(), actualValue.get(2).getPaymentType());
        Assertions.assertEquals(expectedValue3.getPaymentName(), actualValue.get(2).getPaymentName());
        Assertions.assertEquals(expectedValue3.getImagePath(), actualValue.get(2).getImagePath());

        Assertions.assertEquals(expectedValue4.getPaymentType(), actualValue.get(3).getPaymentType());
        Assertions.assertEquals(expectedValue4.getPaymentName(), actualValue.get(3).getPaymentName());
        Assertions.assertEquals(expectedValue4.getImagePath(), actualValue.get(3).getImagePath());

        Assertions.assertEquals(expectedValue5.getPaymentType(), actualValue.get(4).getPaymentType());
        Assertions.assertEquals(expectedValue5.getPaymentName(), actualValue.get(4).getPaymentName());
        Assertions.assertEquals(expectedValue5.getImagePath(), actualValue.get(4).getImagePath());
    }

    @Test
    @DisplayName("[Positive] Get payment method by payment name")
    void testPositiveSearchPaymentMethodByName(){
        String paymentName = "Mandiri";
        Optional<PaymentMethods> paymentMethods = dataDummyPaymentMethod.getPaymentByName(paymentName);
        PaymentMethods paymentData = paymentMethods.get();
        Mockito.when(paymentMethodRepository.findByName(paymentName)).thenReturn(paymentData);
        var actualValue = paymentMethodService.searchPaymentByName(paymentName);

        Assertions.assertNotNull(paymentData);
        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(paymentName, actualValue.getPaymentName());

    }

    @Test
    @DisplayName("[Negative] Get payment method by payment name")
    void testNegativeSearchPaymentMethodByName(){
        String paymentName = "Mandiri";
        DataNotFoundException exception = Assertions.assertThrows(DataNotFoundException.class, () -> {
            Mockito.when(paymentMethodRepository.findByName(dataPaymentMethods.get(0).getPaymentName())).thenReturn(null);
            paymentMethodService.searchPaymentByName(paymentName);
        });
        var expectedValue = "Payment method not found";
        Assertions.assertEquals(expectedValue, exception.getMessage());
    }

    @Test
    @DisplayName("[Positive] Update payment method")
    void testPositiveUpdatePaymentMethod(){
        PaymentMethodRequest dataUpdatePayment = new PaymentMethodRequest();
        dataUpdatePayment.setPaymentType("Mobile Banking");
        dataUpdatePayment.setPaymentName("Mandiri");

        PaymentMethodRequest paymentMethodRequest = dataPaymentMethodRequest.get(2);
        Optional<PaymentMethods> paymentMethods = dataDummyPaymentMethod.getPaymentByName(paymentMethodRequest.getPaymentName());

        PaymentMethods paymentData = paymentMethods.get();

        Mockito.when(paymentMethodRepository.findByName(paymentMethodRequest.getPaymentName())).thenReturn(paymentData);

        paymentData.setPaymentType(dataUpdatePayment.getPaymentType());

        Mockito.when(paymentMethodRepository.save(paymentData)).thenReturn(paymentData);

        var actualValue = paymentMethodService.updatePayment(dataUpdatePayment, paymentMethodRequest.getPaymentName());

        Assertions.assertNotNull(paymentData);
        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(dataUpdatePayment.getPaymentType(), actualValue.getPaymentType());
    }

    @Test
    @DisplayName("[Negative] Update payment method")
    void testNegativeUpdatePaymentMethod(){
        String paymentName =  "BCA";

        DataNotFoundException exception = Assertions.assertThrows(DataNotFoundException.class, () -> {
            Mockito.when(paymentMethodRepository.findByName(paymentName)).thenReturn(null);
            paymentMethodService.updatePayment(dataPaymentMethodRequest.get(0), paymentName);
        });

        DataAlreadyExistException exception2 = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            Mockito.when(paymentMethodRepository.findByName(paymentName)).thenReturn(dataPaymentMethods.get(1));
            paymentMethodService.updatePayment(dataPaymentMethodRequest.get(0), paymentName);
        });

        Assertions.assertEquals("Payment method not found", exception.getMessage());
        Assertions.assertEquals("Payment method with this name already exist", exception2.getMessage());
    }

    @Test
    @DisplayName("[Positive] Delete payment method")
    void testPositiveDeletePaymentMethod(){
        String paymentName =  "BCA";

        Optional<PaymentMethods> paymentMethods = dataDummyPaymentMethod.getPaymentByName(paymentName);

        PaymentMethods paymentData = paymentMethods.get();

        Mockito.when(paymentMethodRepository.findByName(paymentName)).thenReturn(paymentData);
        Mockito.doNothing().when(paymentMethodRepository).deleteById(paymentData.getPaymentId());

        var actualValue = paymentMethodService.deletePayment(paymentName);
        var expectedValue = true;

        Assertions.assertNotNull(paymentData);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("[Negative] Delete payment method")
    void testNegativeDeletePaymentMethod(){
        String paymentName = "Mandiri";
        DataNotFoundException exception = Assertions.assertThrows(DataNotFoundException.class, () -> {
            Mockito.when(paymentMethodRepository.findByName(dataPaymentMethods.get(0).getPaymentName())).thenReturn(null);
            paymentMethodService.deletePayment(paymentName);
        });
        var expectedValue = "Payment method not found";
        Assertions.assertEquals(expectedValue, exception.getMessage());
    }
}
