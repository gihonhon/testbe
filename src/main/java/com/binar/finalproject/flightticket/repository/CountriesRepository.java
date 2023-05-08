package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CountriesRepository extends JpaRepository<Countries, String> {
    @Query("SELECT r FROM Countries r WHERE LOWER(r.countryName) LIKE LOWER(:countryName)")
    Countries findByCountriesName(@Param("countryName") String countryName);

    @Query("SELECT r FROM Countries r WHERE LOWER(r.countryName) LIKE LOWER(:countryName)")
    List<Countries> findAllByCountriesName(@Param("countryName") String countryName);
}
