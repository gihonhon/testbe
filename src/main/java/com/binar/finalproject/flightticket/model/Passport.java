package com.binar.finalproject.flightticket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passport")
public class Passport {
    @Id
    @GeneratedValue
    @Column(name = "passport_id")
    private UUID passportId;

    @Column(name = "passport_number", columnDefinition = "CHAR(50)")
    private String passportNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "passport_expiry", nullable = false, columnDefinition="DATE")
    private LocalDate passportExpiry;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="traveler_id", nullable = false)
    private TravelerList travelerListPassport;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="passport_country", nullable = false)
    private Countries countriesPassport;
}
