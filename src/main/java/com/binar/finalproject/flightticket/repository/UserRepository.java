package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    @Query("SELECT u FROM Users u WHERE LOWER(u.fullName) LIKE LOWER(:fullName)")
    Users findByName(@Param("fullName") String fullName);
    Optional<Users> findByEmail(@Param("email") String email);
    @Query("SELECT u FROM Users u WHERE (u.telephone) = (:telephone)")
    Users findPhoneNumber(@Param("telephone") String telephone);
    @Query("SELECT u FROM Users u WHERE LOWER(u.googleId) LIKE LOWER(:googleId)")
    Users findByGoogleId(@Param("googleId") String googleId);
    @Query("SELECT u FROM Users u WHERE LOWER(u.email) LIKE LOWER(:email)")
    Users findByGmail(@Param("email") String email);
}
