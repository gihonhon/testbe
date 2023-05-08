package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seats, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM seats s where s.airplane_name = :airplaneName")
    List<Seats> findAllSeatsByAirplaneName(@Param("airplaneName") String airplaneName);

    @Query("SELECT s FROM Seats s WHERE LOWER(s.seatNumber) = LOWER(:seatNumber) AND s.airplanesSeats.airplaneName = LOWER(:airplaneName) ")
    Seats findSeatExist(@Param("seatNumber") String seatNumber, @Param("airplaneName") String airplaneName);
}
