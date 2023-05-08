package com.binar.finalproject.flightticket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "terminals")
public class Terminals {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "terminal_id")
    private Integer terminalId;

    @Column(name = "terminal_name", columnDefinition = "CHAR(3)", nullable = false)
    private String terminalName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iata_code", nullable = false)
    private Airports airportsTerminals;

    @OneToMany(mappedBy = "terminalsGates", cascade = CascadeType.ALL)
    private Set<Gates> gates;
}
