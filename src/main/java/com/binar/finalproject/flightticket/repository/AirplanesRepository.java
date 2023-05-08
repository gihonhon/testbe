package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Airplanes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AirplanesRepository extends JpaRepository <Airplanes, String> {
    @Query(nativeQuery = true, value = "SELECT*FROM schedules s INNER JOIN routes r ON s.route_id = r.route_id INNER JOIN airplanes a ON s.airplane_name = a.airplane_name where r.departure_airport = :departureAirport and r.arrival_airport = :arrivalAirport and s.departure_date = :departureDate")
    List<Airplanes> searchAirplaneTicket(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate);
    @Query("SELECT a FROM Airplanes a WHERE LOWER(a.airplaneName) LIKE LOWER(:airplaneName)")
    Airplanes findByName(@Param("airplaneName") String airplaneName);
    @Query("SELECT a FROM Airplanes a INNER JOIN Schedules s ON s.airplanesSchedules = a.airplaneName INNER JOIN Routes r ON s.routesSchedules = r.routeId where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.price ASC ")
    Page<Airplanes> searchAirplaneTicketOrderByLowerPrice(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT a FROM Airplanes a INNER JOIN Schedules s ON s.airplanesSchedules = a.airplaneName INNER JOIN Routes r ON s.routesSchedules = r.routeId where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.price DESC")
    Page<Airplanes> searchAirplaneTicketOrderByHigherPrice(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT a FROM Airplanes a INNER JOIN Schedules s ON s.airplanesSchedules = a.airplaneName INNER JOIN Routes r ON s.routesSchedules = r.routeId where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.departureTime ASC")
    Page<Airplanes> searchScheduleTicketByEarliestDepartureTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT a FROM Airplanes a INNER JOIN Schedules s ON s.airplanesSchedules = a.airplaneName INNER JOIN Routes r ON s.routesSchedules = r.routeId where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.departureTime DESC")
    Page<Airplanes> searchAirplaneTicketByLatestDepartureTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT a FROM Airplanes a INNER JOIN Schedules s ON s.airplanesSchedules = a.airplaneName INNER JOIN Routes r ON s.routesSchedules = r.routeId where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.arrivalTime ASC")
    Page<Airplanes> searchScheduleTicketByEarliestArrivalTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT a FROM Airplanes a INNER JOIN Schedules s ON s.airplanesSchedules = a.airplaneName INNER JOIN Routes r ON s.routesSchedules = r.routeId where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.arrivalTime DESC")
    Page<Airplanes> searchScheduleTicketByLatestArrivalTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT a FROM Airplanes a INNER JOIN Schedules s ON s.airplanesSchedules = a.airplaneName INNER JOIN Routes r ON s.routesSchedules = r.routeId where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate")
    Page<Airplanes> searchTicket (@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
}
