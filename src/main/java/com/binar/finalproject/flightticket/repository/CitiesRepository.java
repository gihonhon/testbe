package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitiesRepository extends JpaRepository<Cities, String> {
    @Query("SELECT u FROM Cities u WHERE LOWER(u.cityName) LIKE LOWER(:cityName)")
    Cities findByCityName(@Param("cityName") String cityName);

    @Query("SELECT u FROM Cities u WHERE LOWER(u.cityName) LIKE LOWER(:cityName)")
    List<Cities> findAllByCityName(@Param("cityName") String cityName);
}
