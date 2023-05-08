package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM orders o where o.user_id = :userId")
    List<Orders> findAllOrderByUserId(@Param("userId") UUID userId);
    @Query(nativeQuery = true, value = "SELECT * FROM orders o where o.payment_id = :paymentId")
    List<Orders> findAllOrderByPaymentId(@Param("paymentId") Integer paymentId);
    @Query(nativeQuery = true, value = "SELECT * FROM orders o where o.status = :status and o.user_id = :userId")
    List<Orders> findHistoryByStatus(@Param("userId") UUID userId, @Param("status") String status);
}
