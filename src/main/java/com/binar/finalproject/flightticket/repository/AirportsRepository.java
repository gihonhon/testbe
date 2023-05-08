package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Airports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportsRepository extends JpaRepository<Airports, String> {
    @Query("SELECT u FROM Airports u WHERE LOWER(u.airportName) LIKE LOWER(:airportName)")
    Airports findByAirportName(@Param("airportName") String airportName);

    @Query("SELECT u FROM Airports u WHERE LOWER(u.airportName) LIKE LOWER(:airportName)")
    List<Airports> findAllByAirportName(@Param("airportName") String airportName);

    @Query(nativeQuery = true, value = "SELECT a.* FROM airports a INNER JOIN cities c ON a.city_code = c.city_code where c.city_name LIKE :cityName")
    List<Airports> findAllAirportByCity(@Param("cityName") String cityName);
}
