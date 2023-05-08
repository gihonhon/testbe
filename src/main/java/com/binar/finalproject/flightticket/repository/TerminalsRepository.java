package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Terminals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalsRepository extends JpaRepository<Terminals, Integer> {
    @Query("SELECT t FROM Terminals t WHERE LOWER(t.terminalName) LIKE LOWER(:terminalName)")
    Terminals findByTerminalName(@Param("terminalName") String terminalName);

    @Query("SELECT t FROM Terminals t WHERE LOWER(t.terminalName) = LOWER(:terminalName) AND LOWER(t.airportsTerminals.iataCode) = LOWER(:iataCode)")
    Terminals findTerminalExist(@Param("terminalName") String terminalName, @Param("iataCode") String iataCode);
}
