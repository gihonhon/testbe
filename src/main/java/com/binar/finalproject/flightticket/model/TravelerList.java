package com.binar.finalproject.flightticket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "traveler_list")
public class TravelerList {
    @Id
    @GeneratedValue
    @Column(name = "traveler_id")
    private UUID travelerId;

    @Column(name = "type", length = 20 ,nullable = false)
    private String type;

    @Column(name = "title", columnDefinition = "CHAR(4)")
    private String title;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date", nullable = false, columnDefinition="DATE")
    private LocalDate birthDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    private Users usersTravelerList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="nationality", nullable = false)
    private Countries countriesTravelerList;

    @OneToOne(mappedBy = "travelerListPassport", cascade = CascadeType.ALL)
    private Passport passport;

    @OneToOne(mappedBy = "travelerListIdCard", cascade = CascadeType.ALL)
    private IdCard idCard;

    @OneToMany(mappedBy = "travelerListTicket", cascade = CascadeType.ALL)
    private Set<Tickets> tickets;

    @JsonIgnore
    @ManyToMany(mappedBy = "travelerListsOrder", cascade = CascadeType.ALL)
    private List<Orders> orders;
}
