package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethods, Integer> {
    @Query("SELECT p FROM PaymentMethods p WHERE LOWER(p.paymentName) LIKE LOWER(:paymentName)")
    PaymentMethods findByName(@Param("paymentName") String paymentName);
}
