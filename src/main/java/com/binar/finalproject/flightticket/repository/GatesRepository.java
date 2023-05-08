package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Gates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GatesRepository extends JpaRepository<Gates, Integer> {
    @Query("SELECT g FROM Gates g WHERE LOWER(g.gateName) LIKE LOWER(:gateName)")
    Gates findByGatesName(@Param("gateName") String gateName);

    @Query("SELECT g FROM Gates g WHERE LOWER(g.gateName) = LOWER(:gatesName) AND g.terminalsGates.terminalId = :terminalId")
    Gates findGateExist(@Param("gatesName") String gatesName, @Param("terminalId") Integer terminalId);
}
