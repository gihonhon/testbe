package com.binar.finalproject.flightticket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "total_ticket", nullable = false)
    private Integer totalTicket;

    @Column(name = "total_price", nullable = false, precision = 2)
    private Float totalPrice;

    @Column(name = "pnr_code", columnDefinition = "CHAR(6)")
    private String pnrCode;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    private Users usersOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id", nullable = false)
    private PaymentMethods paymentMethodsOrder;

    @OneToMany(mappedBy = "ordersTicket", cascade = CascadeType.ALL)
    private Set<Tickets> tickets;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "schedule_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private List<Schedules> scheduleOrders;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "traveler_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "traveler_id"))
    private List<TravelerList> travelerListsOrder;
}
