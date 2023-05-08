package com.binar.finalproject.flightticket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_methods",
        uniqueConstraints = {
                @UniqueConstraint(name = "payment_name", columnNames = "payment_name"),
        }, schema = "public")
public class PaymentMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "payment_name", length = 30, nullable = false)
    private String paymentName;

    @Column(name = "payment_type", length = 30, nullable = false)
    private String paymentType;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "paymentMethodsOrder", cascade = CascadeType.ALL)
    private Set<Orders> orders;
}
