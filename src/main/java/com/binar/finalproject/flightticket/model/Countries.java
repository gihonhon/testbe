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
@Table(name = "countries",
        uniqueConstraints = {
                @UniqueConstraint(name = "telephone_code", columnNames = "telephone_code")
        })
public class Countries
{
    @Id
    @Column(name = "country_code", columnDefinition = "CHAR(4)")
    private String countryCode;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "telephone_code", columnDefinition = "CHAR(4)", nullable = false)
    private String telephoneCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "countriesCities", cascade = CascadeType.ALL)
    private Set<Cities> cities;

    @OneToMany(mappedBy = "countriesTravelerList", cascade = CascadeType.ALL)
    private Set<TravelerList> travelerList;

    @OneToMany(mappedBy = "countriesPassport", cascade = CascadeType.ALL)
    private Set<Passport> passports;

    @OneToMany(mappedBy = "countriesIdCard", cascade = CascadeType.ALL)
    private Set<IdCard> idCards;
}
