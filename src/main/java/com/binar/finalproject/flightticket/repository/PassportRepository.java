package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PassportRepository extends JpaRepository<Passport, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM passport p where p.traveler_id = :travelerId")
    Passport findAllPassportByTravelerList(@Param("travelerId") UUID travelerId);
}
