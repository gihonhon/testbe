package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Routes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RouteRepository extends JpaRepository<Routes, UUID> {
    @Query("SELECT r FROM Routes r WHERE LOWER(r.departureCity) LIKE LOWER(:departureCity) AND LOWER(r.arrivalCity) LIKE LOWER(:arrivalCity) ")
    List<Routes> findRouteByDepartureCityAndArrivalCity(@Param("departureCity") String departureCity, @Param("arrivalCity") String arrivalCity);
    @Query("SELECT r FROM Routes r WHERE LOWER(r.departureAirport) LIKE LOWER(:departureAirport) AND LOWER(r.arrivalAirport) LIKE LOWER(:arrivalAirport) ")
    List<Routes> findRouteByDepartureAndArrivalAirport(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport);
    @Query(nativeQuery = true, value = "SELECT*FROM schedules s INNER JOIN routes r ON s.route_id = r.route_id INNER JOIN airplanes a ON s.airplane_name = a.airplane_name where r.departure_airport = :departureAirport and r.arrival_airport = :arrivalAirport and s.departure_date = :departureDate")
    List<Routes> searchRouteTicket(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate);
    @Query("SELECT r FROM Routes r INNER JOIN Schedules s ON r.routeId = s.routesSchedules INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.price ASC ")
    Page<Routes> searchRouteTicketOrderByLowerPrice(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT r FROM Routes r INNER JOIN Schedules s ON r.routeId = s.routesSchedules INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.price DESC")
    Page<Routes> searchRouteTicketOrderByHigherPrice(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT r FROM Routes r INNER JOIN Schedules s ON r.routeId = s.routesSchedules INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.departureTime ASC")
    Page<Routes> searchRouteTicketByEarliestDepartureTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT r FROM Routes r INNER JOIN Schedules s ON r.routeId = s.routesSchedules INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.departureTime DESC")
    Page<Routes> searchRouteTicketByLatestDepartureTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT r FROM Routes r INNER JOIN Schedules s ON r.routeId = s.routesSchedules INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.arrivalTime ASC")
    Page<Routes> searchScheduleTicketByEarliestArrivalTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT r FROM Routes r INNER JOIN Schedules s ON r.routeId = s.routesSchedules INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate ORDER BY s.arrivalTime DESC")
    Page<Routes> searchScheduleTicketByLatestArrivalTime(@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
    @Query("SELECT r FROM Routes r INNER JOIN Schedules s ON r.routeId = s.routesSchedules INNER JOIN Airplanes a ON s.airplanesSchedules = a.airplaneName where r.departureAirport = :departureAirport and r.arrivalAirport = :arrivalAirport and s.departureDate = :departureDate")
    Page<Routes> searchTicket (@Param("departureAirport") String departureAirport, @Param("arrivalAirport") String arrivalAirport, @Param("departureDate") LocalDate departureDate, Pageable pageable);
}
