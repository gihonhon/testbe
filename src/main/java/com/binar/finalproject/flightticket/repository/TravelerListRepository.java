package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.TravelerList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TravelerListRepository extends JpaRepository<TravelerList, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM traveler_list t where t.user_id = :userId")
    List<TravelerList> findAllTravelerListByUser(@Param("userId") UUID userId);

    @Query("SELECT t FROM TravelerList t WHERE LOWER(t.lastName) = LOWER(:lastName) AND LOWER(t.firstName) = LOWER(:firstName) AND t.usersTravelerList.userId = :userId")
    TravelerList findTravelerListExist(@Param("lastName") String lastName, @Param("firstName") String firstName, @Param("userId") UUID userId);
}
