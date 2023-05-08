package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.IdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IdCardRepository extends JpaRepository<IdCard, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM id_card i where i.traveler_id = :travelerId")
    IdCard findAllIdCardByTravelerList(@Param("travelerId") UUID travelerId);
}
