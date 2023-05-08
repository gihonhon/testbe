package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Schedules;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedules, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM schedules s where s.airplane_name = :airplaneName")
    List<Schedules> getAllAirplaneSchedule(@Param("airplaneName") String airplaneName);
    @Query(nativeQuery = true, value = "SELECT * FROM schedules s where s.route_id = :routeId")
    List<Schedules> getAllRouteSchedule(@Param("routeId") UUID routeId);

    @Query(nativeQuery = true, value = "SELECT*FROM schedules s INNER JOIN routes r ON s.route_id = r.route_id INNER JOIN airplanes a ON s.airplane_name = a.airplane_name where r.departure_airport = :departureAirport and r.arrival_airport = :arrivalAirport and s.departure_date = :departureDate")
    List<Schedules> searchScheduleTicket(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate);

    @Query("SELECT s FROM Schedules s INNER JOIN Routes r ON s.routesSchedules = r.routeId INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.price ASC")
    Page<Schedules> searchScheduleTicketOrderByLowerPrice(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT s FROM Schedules s INNER JOIN Routes r ON s.routesSchedules = r.routeId INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.price DESC")
    Page<Schedules> searchScheduleTicketOrderByHigherPrice(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT s FROM Schedules s INNER JOIN Routes r ON s.routesSchedules = r.routeId INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.departureTime ASC")
    Page<Schedules> searchScheduleTicketByEarliestDepartureTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT s FROM Schedules s INNER JOIN Routes r ON s.routesSchedules = r.routeId INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.departureTime DESC")
    Page<Schedules> searchScheduleTicketByLatestDepartureTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT s FROM Schedules s INNER JOIN Routes r ON s.routesSchedules = r.routeId INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.arrivalTime ASC")
    Page<Schedules> searchScheduleTicketByEarliestArrivalTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT s FROM Schedules s INNER JOIN Routes r ON s.routesSchedules = r.routeId INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.arrivalTime DESC")
    Page<Schedules> searchScheduleTicketByLatestArrivalTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT s FROM Schedules s INNER JOIN Routes r ON s.routesSchedules = r.routeId INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate")
    Page<Schedules> searchTicket (@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
}
